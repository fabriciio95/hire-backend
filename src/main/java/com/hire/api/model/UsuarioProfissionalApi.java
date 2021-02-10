package com.hire.api.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(Include.NON_NULL)
public class UsuarioProfissionalApi {
	
	public enum EuQuero {
		SER_CONTRATADO, CONTRATAR, AMBOS;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotBlank(message = "Usu�rio n�o pode estar em branco.")
	@Size(max = 10, message = "Usu�rio pode ter no m�ximo 10 caracteres")
	private String usuario;
	@NotBlank(message = "Senha n�o pode estar em branco.")
	private String senha;
	@NotBlank(message = "Nome n�o pode estar em branco.")
	@Size(max = 25, message = "Nome s� pode ter at� 25 caracteres.")
	private String nome;
	
	@NotBlank(message = "Endere�o n�o pode estar em branco.")
	@Size(max = 45, message = "Endere�o n�o pode ter mais do que 45 caracteres.")
	private String endereco;
	@NotBlank(message = "E-mail n�o pode estar em branco.")
	@Size(message = "E-mail n�o pode ter mais do que 30 caracteres.", max = 30)
	@Email
	private String email;
	@NotBlank(message = "Descri��o n�o pode estar em branco.")
	@Size(max = 55, message = "Descri��o pode ter no m�ximo 55 caracteres.")
	private String descricao;
	@NotBlank(message = "Valor por hora n�o pode estar em branco.")
	@Size(max = 15, message = "Valor por hora s� pode ter no m�ximo 15 caracteres.")
	private String valorHora;
	@NotBlank(message = "Telefone n�o pode estar em branco.")
	@Size(max = 15, message = "Telefone pode ter no m�ximo 15 caracteres.")
	private String telefone;
	private EuQuero euQuero;
	
	@NotBlank
	private String fotoBase64;

}
