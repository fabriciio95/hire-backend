package com.hire.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hire.domain.model.Avaliacao;

@Repository
public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Long> {

	List<Avaliacao> findByProfissional_Id(Long idProfissional);
	
	void deleteByProfissional_Id(Long idProfissional);
}
