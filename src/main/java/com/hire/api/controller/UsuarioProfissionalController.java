package com.hire.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.hire.api.model.UsuarioProfissionalApi;
import com.hire.domain.model.Profissional;
import com.hire.domain.model.Usuario;
import com.hire.domain.repository.ProfissionalRepository;
import com.hire.domain.repository.UsuarioRepository;
import com.hire.domain.utils.UsuarioUtils;

@RestController
public class UsuarioProfissionalController {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private ProfissionalRepository profissionalRepository;

	@GetMapping(path = "/usuario-profissional/{idUsuario}")
	public ResponseEntity<UsuarioProfissionalApi> obterUsuarioProfissionalApi(@PathVariable("idUsuario") Long idUsuario) {
		Usuario usuario = usuarioRepository.findById(idUsuario).orElse(null);
		if(usuario == null) {
			return ResponseEntity.notFound().build();
		}
		
		Profissional profissional = profissionalRepository.findById(usuario.getId()).orElse(new Profissional());
		
		UsuarioProfissionalApi usuarioProfissionalApi = UsuarioUtils.fromUsuarioAndProfissionalForUsuarioApi(usuario, profissional);
		usuarioProfissionalApi.setFotoBase64(null);
		usuarioProfissionalApi.setSenha(null);
		
		return ResponseEntity.ok(usuarioProfissionalApi);
		
	}
}
