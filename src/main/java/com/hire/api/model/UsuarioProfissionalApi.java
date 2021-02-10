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
	@NotBlank(message = "Usuário não pode estar em branco.")
	@Size(max = 10, message = "Usuário pode ter no máximo 10 caracteres")
	private String usuario;
	@NotBlank(message = "Senha não pode estar em branco.")
	private String senha;
	@NotBlank(message = "Nome não pode estar em branco.")
	@Size(max = 25, message = "Nome só pode ter até 25 caracteres.")
	private String nome;
	
	@NotBlank(message = "Endereço não pode estar em branco.")
	@Size(max = 45, message = "Endereço não pode ter mais do que 45 caracteres.")
	private String endereco;
	@NotBlank(message = "E-mail não pode estar em branco.")
	@Size(message = "E-mail não pode ter mais do que 30 caracteres.", max = 30)
	@Email
	private String email;
	@NotBlank(message = "Descrição não pode estar em branco.")
	@Size(max = 55, message = "Descrição pode ter no máximo 55 caracteres.")
	private String descricao;
	@NotBlank(message = "Valor por hora não pode estar em branco.")
	@Size(max = 15, message = "Valor por hora só pode ter no máximo 15 caracteres.")
	private String valorHora;
	@NotBlank(message = "Telefone não pode estar em branco.")
	@Size(max = 15, message = "Telefone pode ter no máximo 15 caracteres.")
	private String telefone;
	private EuQuero euQuero;
	
	@NotBlank
	private String fotoBase64;

}
