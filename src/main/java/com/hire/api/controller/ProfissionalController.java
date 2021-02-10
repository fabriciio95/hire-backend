package com.hire.api.controller;

import java.nio.file.Paths;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.hire.api.model.ProfissionalApi;
import com.hire.api.model.UsuarioProfissionalApi;
import com.hire.domain.model.Usuario;
import com.hire.domain.repository.UsuarioRepository;
import com.hire.domain.service.ArquivoService;
import com.hire.domain.service.CrudProfissionalService;
import com.hire.domain.service.CrudUsuarioService;

@RestController
@RequestMapping(path = "/profissional")
public class ProfissionalController {
	
	@Autowired
	private CrudProfissionalService crudProfissionalService;
	
	@Autowired
	private ArquivoService arquivoService;

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private CrudUsuarioService crudUsuarioService;
	
	@Value("${hire.files.image}")
	private String dirImages;
	
	
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public UsuarioProfissionalApi adicionarProfissional(@RequestBody @Valid UsuarioProfissionalApi usuarioApi) {
		UsuarioProfissionalApi usuario = crudUsuarioService.salvar(usuarioApi);
		usuario.setSenha(null);
		usuario.setFotoBase64(null);
		return usuario;
	}
	
	@PutMapping("/{idUsuario}")
	public ResponseEntity<UsuarioProfissionalApi> atualizarProfissional(@PathVariable(name = "idUsuario") Long id,
			@RequestBody() @Valid UsuarioProfissionalApi usuarioApi) {
		if(!usuarioRepository.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		
		usuarioApi.setId(id);
		UsuarioProfissionalApi usuario = crudUsuarioService.salvar(usuarioApi);
		usuario.setSenha(null);
		usuario.setFotoBase64(null);;
		return ResponseEntity.ok(usuario);
	}

	
	@GetMapping("/perfil/{idProfissional}")
	public ResponseEntity<ProfissionalApi> getPerfilProfissional(@PathVariable(name = "idProfissional") Long idProfissional) {
		ProfissionalApi profissionalApi = crudProfissionalService.getProfissionalAndAvaliacoes(idProfissional);
		if(profissionalApi.getId() == null) {
			return ResponseEntity.notFound().build();
		}
		
		Usuario usuario = usuarioRepository.findById(profissionalApi.getId()).orElseThrow();
		String imageBase64 = arquivoService.getImage(Paths.get(dirImages, usuario.getNomeFoto()), "png");
		profissionalApi.setFotoBase64(imageBase64);
		
		
		return ResponseEntity.ok(profissionalApi);
	}
}
