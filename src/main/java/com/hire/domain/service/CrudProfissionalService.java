package com.hire.domain.service;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.hire.api.model.ProfissionalApi;
import com.hire.domain.model.Avaliacao;
import com.hire.domain.model.Profissional;
import com.hire.domain.model.Usuario;
import com.hire.domain.repository.AvaliacaoRepository;
import com.hire.domain.repository.ProfissionalRepository;
import com.hire.domain.repository.UsuarioRepository;
import com.hire.domain.utils.ProfissionalUtils;

@Service
public class CrudProfissionalService {
	
	@Autowired
	private ProfissionalRepository profissionalRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private AvaliacaoRepository avaliacaoRepository;
	
	@Autowired
	private ArquivoService arquivoService;
	
	@Value("${hire.files.image}")
	private String dirImages;
	
	public List<ProfissionalApi> findProfissionalPorDescricao(String descricao) { 
		List<Profissional> profissionalExistente = profissionalRepository.findByDescricaoIgnoreCaseContaining(descricao);
		List<ProfissionalApi> profissionaisApi = new ArrayList<>();
		for(Profissional p : profissionalExistente) {
			Usuario usuarioExistente = usuarioRepository.findById(p.getId()).orElse(null);
			if(usuarioExistente != null) {
				Usuario usuario = new Usuario();
				usuario.setNome(usuarioExistente.getNome());
				usuario.setId(usuarioExistente.getId());
				usuario.setFotoBase64(arquivoService.getImage(Paths.get(dirImages, usuarioExistente.getNomeFoto()), "png"));
				Profissional profissional = new Profissional();
				profissional.setValorHora(p.getValorHora());
				profissional.setDescricao(p.getDescricao());
				profissionaisApi.add(ProfissionalUtils.fromUsuarioAndProfissionalForProfissionalApi(usuario, profissional));
			} 
		}
		return profissionaisApi;
	}
	
	public ProfissionalApi getProfissionalAndAvaliacoes(Long id) {
		Profissional profissional = profissionalRepository.findById(id).orElseThrow();
		Usuario usuarioExistente = usuarioRepository.findById(profissional.getId()).orElseThrow();
		Usuario usuario = new Usuario();
		usuario.setNome(usuarioExistente.getNome());
		usuario.setId(profissional.getId());
		usuario.setFotoBase64(arquivoService.getImage(Paths.get(dirImages, usuarioExistente.getNomeFoto()), "png"));
		List<Avaliacao> avaliacoes = avaliacaoRepository.findByProfissional_Id(profissional.getId());
		avaliacoes.sort((a1, a2) -> -a1.getId().compareTo(a2.getId()));
		for(Avaliacao avaliacao : avaliacoes) {
			String imageAutor = arquivoService.getImage(Paths.get(dirImages, avaliacao.getAutor().getNomeFoto()), "png");
			avaliacao.setFotoAutorBase64(imageAutor);
		}
		return ProfissionalUtils.fromUsuarioAndProfissionalAndAvaliacaoForProfissionalApi(usuario, profissional, avaliacoes);
	}

}
