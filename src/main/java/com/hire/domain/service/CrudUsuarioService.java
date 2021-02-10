package com.hire.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hire.api.model.UsuarioProfissionalApi;
import com.hire.api.model.UsuarioProfissionalApi.EuQuero;
import com.hire.domain.exception.UsuarioJaCadastradoException;
import com.hire.domain.model.Profissional;
import com.hire.domain.model.Usuario;
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
	
	@Transactional(rollbackFor = Exception.class)
	public UsuarioProfissionalApi salvar(UsuarioProfissionalApi usuarioApi) { 
		Usuario usuarioExistente = usuarioRepository.findByUsuario(usuarioApi.getUsuario());
		if(usuarioExistente != null && !usuarioExistente.getId().equals(usuarioApi.getId())) {
			throw new UsuarioJaCadastradoException("Usuário já cadastrado");
		}
		
		Usuario usuario = usuarioRepository.save(UsuarioUtils.fromUsuarioAPIForUsuario(usuarioApi));
		usuario.setFotoBase64(usuarioApi.getFotoBase64());
		arquivoService.salvarImagem(usuario);
		
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
			profissional.setValorHora(usuarioApi.getValorHora());
			
			profissional = profissionalRepository.save(profissional);
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
