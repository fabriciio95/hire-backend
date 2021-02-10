package com.hire.domain.utils;

import java.util.List;
import java.util.stream.Collectors;

import com.hire.api.model.AvaliacaoApi;
import com.hire.api.model.ProfissionalApi;
import com.hire.domain.model.Avaliacao;
import com.hire.domain.model.Profissional;
import com.hire.domain.model.Usuario;

public class ProfissionalUtils {

	public static ProfissionalApi fromUsuarioAndProfissionalAndAvaliacaoForProfissionalApi(Usuario usuario, Profissional profissional, 
			List<Avaliacao> avaliacoes) {
		ProfissionalApi profissionalApi = fromUsuarioAndProfissionalForProfissionalApi(usuario, profissional);
		profissionalApi.setAvaliacoes(avaliacoes.stream().map(a -> fromAvaliacaoForAvaliacaoApi(a)).collect(Collectors.toList()));
		return profissionalApi;
	}
	
	public static AvaliacaoApi fromAvaliacaoForAvaliacaoApi(Avaliacao avaliacao) {
		AvaliacaoApi avaliacaoApi = new AvaliacaoApi();
		avaliacaoApi.setId(avaliacao.getId());
		avaliacaoApi.setComentario(avaliacao.getComentario());
		avaliacaoApi.setIdAutor(avaliacao.getAutor().getId());
		avaliacaoApi.setNomeAutor(avaliacao.getAutor().getNome());
		avaliacaoApi.setIdProfissional(avaliacao.getProfissional().getId());
		avaliacaoApi.setFotoAutorBase64(avaliacao.getFotoAutorBase64());
		return avaliacaoApi;
	}
	
	
	public static  ProfissionalApi fromUsuarioAndProfissionalForProfissionalApi(Usuario usuario, Profissional profissional) {
		ProfissionalApi profissionalApi = new ProfissionalApi();
		profissionalApi.setId(usuario.getId());
		profissionalApi.setNome(usuario.getNome());
		profissionalApi.setDescricao(profissional.getDescricao());
		profissionalApi.setEmail(profissional.getEmail());
		profissionalApi.setEndereco(profissional.getEndereco());
		profissionalApi.setTelefone(profissional.getTelefone());
		profissionalApi.setValorHora(profissional.getValorHora());
		return profissionalApi;
	}
}
