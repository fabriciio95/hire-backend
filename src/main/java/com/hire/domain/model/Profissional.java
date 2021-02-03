package com.hire.domain.model;



import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
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
	@Column(name = "id")
	private Integer id;

	@OneToOne
	@MapsId
	private Usuario usuario;
	private String endereco;
	private String email;
	private String descricao;
	private String valorHora;
	private String telefone;
	
	@OneToMany(mappedBy = "profissional")
	private List<Avaliacao> avaliacoes;
}
