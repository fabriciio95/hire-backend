package com.hire.domain.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.hire.domain.model.Profissional;

@Repository
public interface ProfissionalRepository extends PagingAndSortingRepository<Profissional, Long> {

}
