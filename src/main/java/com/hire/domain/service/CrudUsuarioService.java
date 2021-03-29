package com.hire.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hire.api.model.UsuarioProfissionalApi;
import com.hire.api.model.UsuarioProfissionalApi.EuQuero;
import com.hire.domain.exception.ArquivoException;
import com.hire.domain.exception.UsuarioJaCadastradoException;
import com.hire.domain.model.Profissional;
import com.hire.domain.model.Usuario;
import com.hire.domain.repository.AvaliacaoRepository;
import com.hire.domain.repository.ProfissionalRepository;
import com.hire.domain.repository.UsuarioRepository;
import com.hire.domain.utils.UsuarioUtils;

@Service
public class CrudUsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private ProfissionalRepository profissionalRepository;
	
	@Autowired
	private ArquivoService arquivoService;
	
	@Autowired
	private AvaliacaoRepository avaliacaoRepository;
	
	@Transactional(rollbackFor = Exception.class)
	public UsuarioProfissionalApi salvar(UsuarioProfissionalApi usuarioApi) {
		if(usuarioApi.getId() == null && usuarioApi.getFotoBase64().isBlank()) {
			throw new ArquivoException("É obrigatário armazenar uma foto para identificação");
		}
		
		Usuario usuarioExistente = usuarioRepository.findByUsuario(usuarioApi.getUsuario());
		if(usuarioExistente != null && !usuarioExistente.getId().equals(usuarioApi.getId())) {
			throw new UsuarioJaCadastradoException("Usuário já cadastrado");
		}
		Profissional profissionalExistente = profissionalRepository.findByEmail(usuarioApi.getEmail());
		if(profissionalExistente != null && !profissionalExistente.getId().equals(usuarioApi.getId())) {
			throw new UsuarioJaCadastradoException("E-mail já cadastrado");
		}
		
		boolean isEnconderPassword = true;
		if(usuarioApi.getId() != null  && usuarioApi.getSenha() == null ) {
			Usuario usuarioBD = usuarioRepository.findById(usuarioApi.getId()).orElseThrow();
			usuarioApi.setSenha(usuarioBD.getSenha());
			isEnconderPassword = false;
		}
		
		Usuario usuario = usuarioRepository.save(UsuarioUtils.fromUsuarioAPIForUsuario(usuarioApi));
		
		if(usuarioApi.getId() == null || (usuarioApi.getId() != null && !usuarioApi.getSenha().isBlank() && isEnconderPassword)) {
			usuario.criptografarSenha();
		}
		
		if(usuarioApi.getId() != null) {
			usuario.setNomeFoto(String.format("%d.png", usuarioApi.getId()));
		}
		
		if(usuarioApi.getFotoBase64() != null && !usuarioApi.getFotoBase64().isBlank()) {
			usuario.setFotoBase64(usuarioApi.getFotoBase64());
			arquivoService.salvarImagem(usuario);
		}
		
		Profissional profissional = new Profissional();
		if(!usuarioApi.getEuQuero().equals(EuQuero.CONTRATAR)) {
			if(profissionalRepository.existsById(usuario.getId())) {
				profissional = profissionalRepository.findById(usuario.getId()).orElseThrow();
				profissional.setId(usuario.getId());
			} else {
				profissional.setUsuario(usuario);
			}
			
			profissional.setDescricao(usuarioApi.getDescricao());
			profissional.setEmail(usuarioApi.getEmail());
			profissional.setEndereco(usuarioApi.getEndereco());
			profissional.setTelefone(usuarioApi.getTelefone());
			if(!usuarioApi.getValorHora().toUpperCase().contains("/H")) {
				usuarioApi.setValorHora(usuarioApi.getValorHora().concat("/h"));
			}
			profissional.setValorHora(usuarioApi.getValorHora().toLowerCase());
			
			profissional = profissionalRepository.save(profissional);
		} else if(usuarioApi.getEuQuero().equals(EuQuero.CONTRATAR) && profissionalRepository.existsById(usuario.getId())) {
			avaliacaoRepository.deleteByProfissional_Id(usuario.getId());
			profissionalRepository.deleteById(usuario.getId());
		}
		
		return UsuarioUtils.fromUsuarioAndProfissionalForUsuarioApi(usuario, profissional);
	}
	
	public void delete(Long usuarioId) {
		Profissional profissional = profissionalRepository.findById(usuarioId).orElse(null);
		if(profissional != null) {
			profissionalRepository.deleteById(usuarioId);
		}
		usuarioRepository.deleteById(usuarioId);
	}
	
}
