package com.hire.domain.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Avaliacao {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Long id;
	@NotBlank(message = "Comentário não pode estar em branco.")
	@Size(max = 140, message = "Comentário só pode ter no máximo 140 caracteres.")
	private String comentario;
	@ManyToOne
	@JoinColumn
	@NotNull
	private Usuario autor;
	@ManyToOne
	@JoinColumn
	@NotNull
	private Profissional profissional;
	private transient String fotoAutorBase64;

}
