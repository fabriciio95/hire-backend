package com.hire.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hire.domain.model.Profissional;

@Repository
public interface ProfissionalRepository extends JpaRepository<Profissional, Long> {

	Profissional findByEmail(String email);
	
	List<Profissional> findByDescricaoIgnoreCaseContaining(String descricao);
}
