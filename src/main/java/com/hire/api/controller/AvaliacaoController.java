package com.hire.api.controller;

import java.nio.file.Paths;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.hire.api.model.AvaliacaoApi;
import com.hire.domain.exception.AvaliacaoException;
import com.hire.domain.model.Avaliacao;
import com.hire.domain.model.Usuario;
import com.hire.domain.repository.AvaliacaoRepository;
import com.hire.domain.repository.ProfissionalRepository;
import com.hire.domain.repository.UsuarioRepository;
import com.hire.domain.service.ArquivoService;
import com.hire.domain.utils.ProfissionalUtils;

@RestController
@RequestMapping(path = "/avaliacao")
public class AvaliacaoController {

	@Autowired
	private AvaliacaoRepository avaliacaoRepository;
	
	@Autowired
	private ProfissionalRepository profissionalRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Value("${hire.files.image}")
	private String dirImages;
	
	@Autowired
	private ArquivoService arquivoService;
	
	
	@PostMapping("/publicar")
	@ResponseStatus(code = HttpStatus.CREATED)
	public AvaliacaoApi publicarAvaliacao(@RequestBody @Valid AvaliacaoApi avaliacaoApi){
		if(avaliacaoApi.getIdAutor().equals(avaliacaoApi.getIdProfissional())) {
			throw new AvaliacaoException("Você não pode fazer uma avaliação de si mesmo.");
		}
		Avaliacao avaliacao = new Avaliacao();
		avaliacao.setId(avaliacaoApi.getId());
		avaliacao.setComentario(avaliacaoApi.getComentario());
		Usuario autor = usuarioRepository.findById(avaliacaoApi.getIdAutor()).orElseThrow();
		avaliacao.setAutor(autor);
		avaliacao.setFotoAutorBase64(arquivoService.getImage(Paths.get(dirImages, autor.getNomeFoto()), "png"));
		avaliacao.setProfissional(profissionalRepository.findById(avaliacaoApi.getIdProfissional()).orElseThrow());
		avaliacao = avaliacaoRepository.save(avaliacao);
		return ProfissionalUtils.fromAvaliacaoForAvaliacaoApi(avaliacao);
	}
}
