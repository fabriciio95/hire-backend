package com.hire.api.controller;

import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.hire.api.model.ProfissionalApi;
import com.hire.domain.model.Usuario;
import com.hire.domain.repository.UsuarioRepository;
import com.hire.domain.service.ArquivoService;
import com.hire.domain.service.CrudProfissionalService;

@RestController
public class SearchController {

	@Autowired
	private CrudProfissionalService crudProfissionalService;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private ArquivoService arquivoService;
	
	@Value("${hire.files.image}")
	private String dirImages;
	
	@GetMapping("/search/{descricao}")
	public ResponseEntity<List<ProfissionalApi>> searchByDescription(@PathVariable(name = "descricao") String descricao){
		List<ProfissionalApi> profissionalApi = crudProfissionalService.findProfissionalPorDescricao(descricao);
		if(profissionalApi.isEmpty()) {
			return ResponseEntity.notFound().build();
		} 
		
		for(ProfissionalApi profissional : profissionalApi) {
			Usuario usuario = usuarioRepository.findById(profissional.getId()).orElseThrow();
			String imageMiniaturaBase64 = arquivoService.getImage(Paths.get(dirImages, usuario.montarNomeFotoMiniatura()), "png");
			profissional.setFotoBase64(imageMiniaturaBase64);
			profissional.setAvaliacoes(null);
		}
		return ResponseEntity.ok(profissionalApi);
	}
}
