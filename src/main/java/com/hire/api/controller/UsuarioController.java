package com.hire.api.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.hire.api.model.UsuarioProfissionalApi;
import com.hire.api.model.UsuarioProfissionalApi.EuQuero;
import com.hire.domain.exception.NotBlankException;
import com.hire.domain.model.Usuario;
import com.hire.domain.repository.UsuarioRepository;
import com.hire.domain.service.CrudUsuarioService;
import com.hire.domain.utils.UsuarioUtils;

@RestController
@RequestMapping(path = "/usuario")
public class UsuarioController {

	@Autowired
	private CrudUsuarioService crudUsuarioService;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public Usuario adicionarUsuario(@RequestBody @Valid Usuario usuario) {
		if(usuario.getSenha() == null || usuario.getSenha().isBlank()) {
			throw new NotBlankException("A senha não pode estar em branco.");
		}
		UsuarioProfissionalApi usuarioApi = UsuarioUtils.fromUsuarioForUsuarioProfissionalApi(usuario);
		usuarioApi.setEuQuero(EuQuero.CONTRATAR);
		Usuario usuarioSalvo = UsuarioUtils.fromUsuarioAPIForUsuario(crudUsuarioService.salvar(usuarioApi));
		usuarioSalvo.setSenha(null);
		usuarioSalvo.setFotoBase64(null);
		return usuarioSalvo;
	}
	
	
	@PutMapping("/{idUsuario}")
	public ResponseEntity<Usuario> atualizarUsuario(@PathVariable(name = "idUsuario") Long id, @RequestBody @Valid Usuario usuario) {
		if(!usuarioRepository.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		
		usuario.setId(id);
		UsuarioProfissionalApi usuarioApi = (UsuarioUtils.fromUsuarioForUsuarioProfissionalApi(usuario));
		
		usuarioApi.setEuQuero(EuQuero.CONTRATAR);
		usuarioApi = crudUsuarioService.salvar(usuarioApi);
		
		Usuario usuarioRetornado = UsuarioUtils.fromUsuarioAPIForUsuario(usuarioApi);
		usuarioRetornado.setSenha(null);
		usuarioRetornado.setFotoBase64(null);
		return ResponseEntity.ok(usuarioRetornado);
	}
}
