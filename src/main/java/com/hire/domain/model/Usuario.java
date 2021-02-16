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
	@NotBlank(message = "Usu�rio n�o pode estar em branco.")
	@Size(max = 10, message = "Usu�rio pode ter no m�ximo 10 caracteres.")
	@Column(unique = true)
	private String usuario;
	@NotBlank(message = "Senha n�o pode estar em branco.")
	private String senha;
	@NotBlank(message = "Nome n�o pode estar em branco.")
	@Size(max = 25, message = "Nome s� pode ter at� 25 caracteres.")
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
