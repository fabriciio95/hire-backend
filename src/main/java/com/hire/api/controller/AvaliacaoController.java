package com.hire.api.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.hire.api.model.AvaliacaoApi;
import com.hire.domain.model.Avaliacao;
import com.hire.domain.repository.AvaliacaoRepository;
import com.hire.domain.repository.ProfissionalRepository;
import com.hire.domain.repository.UsuarioRepository;
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
	
	
	@PostMapping("/publicar")
	@ResponseStatus(code = HttpStatus.CREATED)
	public AvaliacaoApi publicarAvaliacao(@RequestBody @Valid AvaliacaoApi avaliacaoApi){
		Avaliacao avaliacao = new Avaliacao();
		avaliacao.setId(avaliacaoApi.getId());
		avaliacao.setComentario(avaliacaoApi.getComentario());
		avaliacao.setAutor(usuarioRepository.findById(avaliacaoApi.getIdAutor()).orElseThrow());
		avaliacao.setProfissional(profissionalRepository.findById(avaliacaoApi.getIdProfissional()).orElseThrow());
		avaliacao = avaliacaoRepository.save(avaliacao);
		return ProfissionalUtils.fromAvaliacaoForAvaliacaoApi(avaliacao);
	}
}
