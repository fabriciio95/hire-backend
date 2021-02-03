package com.hire.domain.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.hire.domain.model.Usuario;

@Repository
public interface UsuarioRepository extends PagingAndSortingRepository<Usuario, Long>{

}
