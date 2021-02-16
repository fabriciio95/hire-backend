package com.hire.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@JsonInclude(Include.NON_NULL)
public class Usuario {

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotBlank(message = "Usuário não pode estar em branco.")
	@Size(max = 10, message = "Usuário pode ter no máximo 10 caracteres.")
	@Column(unique = true)
	private String usuario;
	@NotBlank(message = "Senha não pode estar em branco.")
	private String senha;
	@NotBlank(message = "Nome não pode estar em branco.")
	@Size(max = 25, message = "Nome só pode ter até 25 caracteres.")
	private String nome;
	private String nomeFoto;
	private transient String fotoBase64;
	
	public void definirNomeFoto() {
		if(nomeFoto == null && id != null && fotoBase64 != null) {
			setNomeFoto(String.format("%d.png", id));
		}
	}
	
	public String montarNomeFotoMiniatura() {
		String[] partesNome = nomeFoto.split("\\.");
	    String parte1Nome = partesNome[0];
	    String parte2Nome = partesNome[1];
	    return String.format("%s-miniatura.%s", parte1Nome, parte2Nome);
	}
}
