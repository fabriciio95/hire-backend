package com.hire.domain.model;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Profissional {
	
	@Id
	@EqualsAndHashCode.Include
	private Long id;

	@OneToOne
	@MapsId
	private Usuario usuario;
	@NotBlank(message = "Endere�o n�o pode estar em branco.")
	@Size(max = 45, message = "Endere�o n�o pode ter mais do que 45 caracteres.")
	private String endereco;
	@NotBlank(message = "E-mail n�o pode estar em branco.")
	@Size(message = "E-mail n�o pode ter mais do que 30 caracteres.", max = 30)
	@Email
	@Column(unique = true)
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

}
