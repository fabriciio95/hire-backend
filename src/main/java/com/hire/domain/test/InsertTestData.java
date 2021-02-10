package com.hire.domain.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.hire.domain.model.Avaliacao;
import com.hire.domain.model.Profissional;
import com.hire.domain.model.Usuario;
import com.hire.domain.repository.AvaliacaoRepository;
import com.hire.domain.repository.ProfissionalRepository;
import com.hire.domain.repository.UsuarioRepository;

import lombok.Getter;
import lombok.Setter;

@Component
@Getter
@Setter
public class InsertTestData {

	private UsuarioRepository usuarioRepository;
	private ProfissionalRepository profissionalRepository;
	private AvaliacaoRepository avaliacaoRepository;
	
	@Autowired
	public InsertTestData(UsuarioRepository usuarioRepository, ProfissionalRepository profissionalRepository, 
			AvaliacaoRepository avaliacaoRepository) {
		this.usuarioRepository = usuarioRepository;
		this.profissionalRepository = profissionalRepository;
		this.avaliacaoRepository = avaliacaoRepository;
	}
	
	@EventListener
	public void onApplicationEvent(ContextRefreshedEvent event) {
		PasswordEncoder enconder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		Usuario usuario = new Usuario();
		usuario.setNome("Juliano Ferreira");
		usuario.setSenha(enconder.encode("a"));
		usuario.setUsuario("juliano");
		usuario.setNomeFoto("1.png");
		usuarioRepository.save(usuario);
		Usuario usuario2 = new Usuario();
		usuario2.setNome("Marcos Lima");
		usuario2.setUsuario("marcos");
		usuario2.setSenha(enconder.encode("a"));
		usuario2.setNomeFoto("2.png");
		usuarioRepository.save(usuario2);
		Profissional profissional = new Profissional();
		profissional.setId(usuario.getId());
		profissional.setEmail("f@gmail.com");
		profissional.setDescricao("Faxina, serviços gerais de casa");
		profissional.setEndereco("Rua das Palmeiras, 849 Centro - Jaquaritiba");
		profissional.setTelefone("(11) 98393-3948");
		profissional.setValorHora("40/h");
		profissional.setUsuario(usuario);
		profissionalRepository.save(profissional);
		Avaliacao avaliacao = new Avaliacao();
		avaliacao.setComentario("Ótimo trabalhador, a faxina aqui em casa foi muito bem feita");
		avaliacao.setProfissional(profissional);
		avaliacao.setAutor(usuario2);
		avaliacaoRepository.save(avaliacao);
	}
}
