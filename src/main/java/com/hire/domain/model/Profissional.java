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
	@NotBlank(message = "Endereço não pode estar em branco.")
	@Size(max = 45, message = "Endereço não pode ter mais do que 45 caracteres.")
	private String endereco;
	@NotBlank(message = "E-mail não pode estar em branco.")
	@Size(message = "E-mail não pode ter mais do que 30 caracteres.", max = 30)
	@Email
	@Column(unique = true)
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

}
