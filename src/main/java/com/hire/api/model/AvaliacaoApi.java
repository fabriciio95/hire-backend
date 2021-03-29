package com.hire.api.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(Include.NON_NULL)
public class AvaliacaoApi {

	private Long id;
	@NotBlank(message = "Comentário não pode estar em branco.")
	@Size(max = 140, message = "Comentário pode ter no máximo 140 caracteres.")
	private String comentario;
	@NotNull
	private Long idAutor;
	private String nomeAutor;
	@NotNull
	private Long idProfissional;
	private String fotoAutorBase64;
}
