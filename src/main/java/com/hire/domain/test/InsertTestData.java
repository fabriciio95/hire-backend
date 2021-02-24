package com.hire.domain.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
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
		Usuario usuario = new Usuario();
		usuario.setNome("Juliano Ferreira");
		usuario.setSenha("a");
		usuario.criptografarSenha();
		usuario.setUsuario("juliano");
		usuario.setNomeFoto("1.png");
		usuarioRepository.save(usuario);
		Usuario usuario2 = new Usuario();
		usuario2.setNome("Marcos Lima");
		usuario2.setUsuario("marcos");
		usuario2.setSenha("a");
		usuario2.criptografarSenha();
		usuario2.setNomeFoto("2.png");
		usuarioRepository.save(usuario2);
		Usuario usuario3 = new Usuario();
		usuario3.setNome("Felipe Cardoso Lopes");
		usuario3.setUsuario("felipe");
		usuario3.setSenha("a");
		usuario3.criptografarSenha();
		usuario3.setNomeFoto("3.png");
		usuarioRepository.save(usuario3);
		Usuario usuario4 = new Usuario();
		usuario4.setNome("Reginaldo Garcia Silva");
		usuario4.setUsuario("reginaldo");
		usuario4.setSenha("a");
		usuario4.criptografarSenha();
		usuario4.setNomeFoto("4.png");
		usuarioRepository.save(usuario4);
		Usuario usuario5 = new Usuario();
		usuario5.setNome("Marina Cristina Lopez");
		usuario5.setUsuario("marina");
		usuario5.setSenha("a");
		usuario5.criptografarSenha();
		usuario5.setNomeFoto("5.png");
		usuarioRepository.save(usuario5);
		Usuario usuario6 = new Usuario();
		usuario6.setNome("Bianca Silva Nunes");
		usuario6.setUsuario("bianca");
		usuario6.setSenha("a");
		usuario6.criptografarSenha();
		usuario6.setNomeFoto("6.png");
		usuarioRepository.save(usuario6);
		Usuario usuario7 = new Usuario();
		usuario7.setNome("Marcelo Costa Filho");
		usuario7.setUsuario("marcelo");
		usuario7.setSenha("a");
		usuario7.criptografarSenha();
		usuario7.setNomeFoto("7.png");
		usuarioRepository.save(usuario7);
		Profissional profissional = new Profissional();
		profissional.setId(usuario.getId());
		profissional.setEmail("f@gmail.com");
		profissional.setDescricao("Faxina, serviços gerais de casa");
		profissional.setEndereco("Rua das Palmeiras, 849 Centro - Jaquaritiba");
		profissional.setTelefone("(11) 98393-3948");
		profissional.setValorHora("40/h");
		profissional.setUsuario(usuario);
		profissionalRepository.save(profissional);
		Profissional profissional2 = new Profissional();
		profissional2.setId(usuario4.getId());
		profissional2.setEmail("r@gmail.com");
		profissional2.setDescricao("Serviços gerais de casa, serviços de mecânico");
		profissional2.setEndereco("Av das Torres, 348 Centro - Carapicuiba");
		profissional2.setTelefone("(11) 93429-2394");
		profissional2.setValorHora("45/h");
		profissional2.setUsuario(usuario4);
		profissionalRepository.save(profissional2);
		Profissional profissional3 = new Profissional();
		profissional3.setId(usuario5.getId());
		profissional3.setEmail("marina@gmail.com");
		profissional3.setDescricao("Faxina, cozinha, babá, cuidar de pets, casa.");
		profissional3.setEndereco("Rua Antonio Guimarães, 28 Barueri");
		profissional3.setTelefone("(11) 98849-4893");
		profissional3.setValorHora("35/h");
		profissional3.setUsuario(usuario5);
		profissionalRepository.save(profissional3);
		Profissional profissional4 = new Profissional();
		profissional4.setId(usuario7.getId());
		profissional4.setEmail("marcelo@gmail.com");
		profissional4.setDescricao("Fotográfia, ensaio, edição de fotos, serviços em casa");
		profissional4.setEndereco("Av General Pedro Pinho, 489 Osasco");
		profissional4.setTelefone("(11) 97850-4990");
		profissional4.setValorHora("45/h");
		profissional4.setUsuario(usuario7);
		profissionalRepository.save(profissional4);
		Avaliacao avaliacao = new Avaliacao();
		avaliacao.setComentario("Ótimo trabalhador, a faxina aqui em casa foi muito bem feita");
		avaliacao.setProfissional(profissional);
		avaliacao.setAutor(usuario2);
		avaliacaoRepository.save(avaliacao);
	}
}
