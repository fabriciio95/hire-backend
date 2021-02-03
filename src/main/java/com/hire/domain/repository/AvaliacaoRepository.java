package com.hire.domain.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.hire.domain.model.Avaliacao;

@Repository
public interface AvaliacaoRepository extends PagingAndSortingRepository<Avaliacao, Long> {

}
