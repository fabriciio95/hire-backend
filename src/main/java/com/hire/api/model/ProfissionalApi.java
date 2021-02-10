package com.hire.api.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(Include.NON_NULL)
public class ProfissionalApi {

	private Long id;
	private String nome;
	private String endereco;
	private String email;
	private String descricao;
	private String valorHora;
	private String telefone;
	private String fotoBase64;
	private List<AvaliacaoApi> avaliacoes = new ArrayList<>();
}
