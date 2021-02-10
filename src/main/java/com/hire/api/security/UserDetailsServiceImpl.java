package com.hire.api.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.hire.domain.model.Usuario;
import com.hire.domain.repository.UsuarioRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	private UsuarioRepository usuarioRepository;
	
	
	@Autowired
	public UserDetailsServiceImpl(UsuarioRepository usuarioRepository) {
		this.usuarioRepository = usuarioRepository;
	}



	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuario = usuarioRepository.findByUsuario(username);
		
		if(usuario == null) {
			throw new UsernameNotFoundException("Usu�rio n�o encontrado");
		}
		
		return new UserDetailsImpl(usuario);
	}

	
}
