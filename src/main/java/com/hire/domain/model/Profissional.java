package com.hire.domain.model;



import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

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
	private String endereco;
	private String email;
	private String descricao;
	private String valorHora;
	private String telefone;

}
