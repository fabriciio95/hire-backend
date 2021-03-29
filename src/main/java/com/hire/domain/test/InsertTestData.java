package com.hire.domain.test;

import java.util.ArrayList;
import java.util.List;

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
import com.hire.domain.utils.RandomUtils;

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
		List<Usuario> users = saveUsers();
		List<Profissional> workers = saveProfissionals(users);
		saveAvaliacoes(workers, users);
		saveFabricio();
	}
	
	private void saveFabricio() {
		Usuario usuario = new Usuario();
		usuario.setNome("Fabricio Siqueira Macedo");
		usuario.setSenha("a");
		usuario.criptografarSenha();
		usuario.setUsuario("fabri");
		usuario = usuarioRepository.save(usuario);
		usuario.setNomeFoto(String.format("%d.png", usuario.getId()));
		usuarioRepository.save(usuario);
		
		Profissional profissional = new Profissional();
		profissional.setId(usuario.getId());
		profissional.setUsuario(usuario);
		profissional.setDescricao("Consertos gerais em casas!");
		profissional.setEmail("fabricio@gmail.com");
		profissional.setEndereco("Rua Areado, 234 - Carapicuiba");
		profissional.setTelefone("(11) 98432-8331");
		profissional.setValorHora("50/h");
		profissionalRepository.save(profissional);
	}
	
	private void saveAvaliacoes(List<Profissional> workers, List<Usuario> users) {
		List<String> comentarios = new ArrayList<>();
		List<Usuario> auxUser = new ArrayList<>();
		for (Profissional worker : workers) {
			if(comentarios.size() < 5) {
				comentarios = getDataAvaliacoes();
			}
			
			if(auxUser.size() < 5) {
				auxUser.addAll(users);
			}
			
			
			
			int qtdComentarios = RandomUtils.random(1, 5);
			for(int i = 0; i < qtdComentarios; i++) {Usuario autor = null;
				do {
					int idUserAutor = RandomUtils.random(0, auxUser.size() - 1);
					autor = auxUser.get(idUserAutor);
				} while(autor.getId().equals(worker.getId()));
				int idComentario = RandomUtils.random(0, comentarios.size() - 1);
				String comentario = comentarios.get(idComentario);
				Avaliacao avaliacao = new Avaliacao();
				avaliacao.setComentario(comentario);
				avaliacao.setProfissional(worker);
				avaliacao.setAutor(autor);
				avaliacaoRepository.save(avaliacao);
				comentarios.remove(idComentario);
				auxUser.remove(autor);
			}
		}
	}
	
	private List<String> getDataAvaliacoes() { 
		List<String> dados = new ArrayList<>();
		dados.add("Ótimo profissional!");
		dados.add("Trabalho muito bem feito!");
		dados.add("Profissional excelente, bem educado e pontual.");
		dados.add("Muito bom profissional, faço minhas recomendações.");
		dados.add("Serviço feito com excelência.");
		dados.add("Faz um serviço muito bom.");
		dados.add("Profissional excelente, contrataria novamente!");
		dados.add("Gostei bastante de seus serviços prestados.");
		dados.add("Recomendo e contrataria novamente.");
		dados.add("Realmente faz um serviço de qualidade.");
		dados.add("Chegou bem atrasado, porém fez bem seu trabalho!");
		dados.add("Apesar da demora, prestou um serviço de qualidade.");
		dados.add("Gosta de falar bastante, mas gostei do serviço.");
		dados.add("Mesmo com sua falta de educação acabei gostando do serviço");
		dados.add("Chegou com muito atraso e ainda um pouco grosso, porém é bom de serviço");
		dados.add("Me arrependi de contratar, não recomendo!!!");
		dados.add("É uma pessoa complemetamente mal educado, e fez um péssimo serviço.");
		dados.add("Não contraria novamente, péssimo profissional.");
		dados.add("Chegou super atrasado e ainda presta um péssimo serviço.");
		dados.add("Não contratem, seu serviço é horrível.");
		return dados;
	}
	
	private List<Profissional> saveProfissionals(List<Usuario> users) {
		List<Profissional> workers = new ArrayList<>();
		List<Usuario> usersAux = new ArrayList<>();
		usersAux.addAll(users);
		List<String> dataWorkes = getDataWorkers();
		int cont = 0;
		while(cont < 15){
			int idUser = RandomUtils.random(0, usersAux.size() - 1);
			Usuario user = usersAux.get(idUser);
			Profissional profissional = new Profissional();
			int dataId = RandomUtils.random(0, dataWorkes.size() - 1);
			profissional.setId(user.getId());
			profissional.setEmail(user.getNome().split(" ")[0].toLowerCase() + "@gmail.com");
			profissional.setUsuario(user);
			String data = dataWorkes.get(dataId);
			profissional.setDescricao(data.split("/")[0]);
			profissional.setEndereco(data.split("/")[1]);
			profissional.setTelefone(data.split("/")[2]);
			profissional.setValorHora(data.split("/")[3] + "/h");
			dataWorkes.remove(dataId);
			profissional = profissionalRepository.save(profissional);
			workers.add(profissional);
			usersAux.remove(idUser);
			cont += 1;
		} 
		
		
		return workers;
	}
	
	private List<String> getDataWorkers() {
		List<String> dados = new ArrayList<>();
		String d1 = String.format("%s/%s/%s/%s", "Faxina, serviços gerais de casa.", "Rua das Palmeiras, 849 Centro - Jaquaritiba", "(11) 98393-3948", "40/h");
		dados.add(d1);
		String d2 = String.format("%s/%s/%s/%s", "Consertos gerais em casa.  ", "Av das Torres, 49 - Santos", "(13) 99303-3843", "45/h");
		dados.add(d2);
		String d3 = String.format("%s/%s/%s/%s", "Eletrecista residencial. ", "Rua Antonio Goveia, 9094 - São Paulo ", "(11) 98493-9438", "60/h");
		dados.add(d3);
		String d4 = String.format("%s/%s/%s/%s", "Mecânica automotiva", "Rua Dos Palmares - São Paulo", "(11) 99943-9949", "70/h");
		dados.add(d4);
		String d5 = String.format("%s/%s/%s/%s", "Faxina, consertos e babá", "Av das Flores, 544 - Barueri", "(11) 97480-4905", "40/h");
		dados.add(d5);
		
		String d6 = String.format("%s/%s/%s/%s", "Carpintaria e Serralheria", "Rua Humaitá, 194 - Carapicuiba", "(11) 96589-9838", "35/h");
		dados.add(d6);
		String d7 = String.format("%s/%s/%s/%s", "Reparos gerais em casa.  ", "Rua Euclides da Cunha, 92 - Osasco", "(11) 97650-3894", "50/h");
		dados.add(d7);
		String d8 = String.format("%s/%s/%s/%s", "Eletrecista automotivo. ", "Av Principal, 762 - São Paulo", "(11) 94002-8922", "50/h");
		dados.add(d8);
		String d9 = String.format("%s/%s/%s/%s", "Babá de idoso", "Av Santos Dumont, 99 - São Paulo", "(11) 98433-3890", "40/h");
		dados.add(d9);
		String d10 = String.format("%s/%s/%s/%s", "Babá, cuido de crianças", "Rua Marechal Deodoro, 199 - São Paulo", "(11) 99354-4900", "45/h");
		dados.add(d10);
		
		String d11 = String.format("%s/%s/%s/%s", "Eletrecista para casas e automóveis", "Rua Bangu, 181 - São Paulo", "(11) 98999-9299", "60/h");
		dados.add(d11);
		String d12 = String.format("%s/%s/%s/%s", "Faxina, cozinho, serviços gerais em casa.  ", "Av Euclides Ferraz, 39 - Osasco", "(11) 98394-9403", "50/h");
		dados.add(d12);
		String d13 = String.format("%s/%s/%s/%s", "Limpo vidros, sofás e faxina ", "Av Miguel Lima, 99 - São Paulo", "(11) 98834-9439", "40/h");
		dados.add(d13);
		String d14 = String.format("%s/%s/%s/%s", "Faço manutenção em aparelhos eletrônicos", "Rua Olegário, 199 - São Paulo", "(11) 99495-5583", "50/h");
		dados.add(d14);
		String d15 = String.format("%s/%s/%s/%s", "Faço cortes de cabelo masculino em casa", "Rua Ferraz de Vasconcellos, 69 - Osasco", "(11) 97340-4005", "30/h");
		dados.add(d15);
		
		return dados;
	}
	
	private List<Usuario> saveUsers() {
		List<Usuario> users = new ArrayList<>();
		
		Usuario usuario = new Usuario();
		usuario.setNome("Juliano Antônio Ferreira");
		usuario.setSenha("a");
		usuario.criptografarSenha();
		usuario.setUsuario("juliano");
		usuario.setNomeFoto("1.png");
		usuario = usuarioRepository.save(usuario);
		users.add(usuario);
		
		Usuario usuario2 = new Usuario();
		usuario2.setNome("Marcos Silva Lima");
		usuario2.setUsuario("marcos");
		usuario2.setSenha("a");
		usuario2.criptografarSenha();
		usuario2.setNomeFoto("2.png");
		usuario2 = usuarioRepository.save(usuario2);
		users.add(usuario2);
		
		Usuario usuario3 = new Usuario();
		usuario3.setNome("Felipe Cardoso Lopes");
		usuario3.setUsuario("felipe");
		usuario3.setSenha("a");
		usuario3.criptografarSenha();
		usuario3.setNomeFoto("3.png");
		usuario3 = usuarioRepository.save(usuario3);
		users.add(usuario3);
		
		Usuario usuario4 = new Usuario();
		usuario4.setNome("Reginaldo Garcia Silva");
		usuario4.setUsuario("reginaldo");
		usuario4.setSenha("a");
		usuario4.criptografarSenha();
		usuario4.setNomeFoto("4.png");
		usuario4 = usuarioRepository.save(usuario4);
		users.add(usuario4);
		
		Usuario usuario5 = new Usuario();
		usuario5.setNome("Marina Cristina Lopez");
		usuario5.setUsuario("marina");
		usuario5.setSenha("a");
		usuario5.criptografarSenha();
		usuario5.setNomeFoto("5.png");
		usuario5 = usuarioRepository.save(usuario5);
		users.add(usuario5);
		
		Usuario usuario6 = new Usuario();
		usuario6.setNome("Bianca Silva Nunes");
		usuario6.setUsuario("bianca");
		usuario6.setSenha("a");
		usuario6.criptografarSenha();
		usuario6.setNomeFoto("6.png");
		usuario6 = usuarioRepository.save(usuario6);
		users.add(usuario6);
		
		Usuario usuario7 = new Usuario();
		usuario7.setNome("Marcelo Costa Filho");
		usuario7.setUsuario("marcelo");
		usuario7.setSenha("a");
		usuario7.criptografarSenha();
		usuario7.setNomeFoto("7.png");
		usuario7 = usuarioRepository.save(usuario7);
		users.add(usuario7);
		
		Usuario usuario8 = new Usuario();
		usuario8.setNome("Beatriz Lima Couto");
		usuario8.setUsuario("bia");
		usuario8.setSenha("a");
		usuario8.criptografarSenha();
		usuario8.setNomeFoto("8.png");
		usuario8 = usuarioRepository.save(usuario8);
		users.add(usuario8);
		
		Usuario usuario9 = new Usuario();
		usuario9.setNome("Fernanda Coelho de Moraes");
		usuario9.setUsuario("nanda");
		usuario9.setSenha("a");
		usuario9.criptografarSenha();
		usuario9.setNomeFoto("9.png");
		usuario9 = usuarioRepository.save(usuario9);
		users.add(usuario9);
		
		Usuario usuario10 = new Usuario();
		usuario10.setNome("Amanda Soares de Ferraz");
		usuario10.setUsuario("amanda");
		usuario10.setSenha("a");
		usuario10.criptografarSenha();
		usuario10.setNomeFoto("10.png");
		usuario10 = usuarioRepository.save(usuario10);
		users.add(usuario10);

		Usuario usuario11 = new Usuario();
		usuario11.setNome("Rafael Costa Veloso");
		usuario11.setSenha("a");
		usuario11.criptografarSenha();
		usuario11.setUsuario("rafael");
		usuario11.setNomeFoto("11.png");
		usuario11 = usuarioRepository.save(usuario11);
		users.add(usuario11);
		
		Usuario usuario12 = new Usuario();
		usuario12.setNome("Joana Pires Motta");
		usuario12.setUsuario("joana");
		usuario12.setSenha("a");
		usuario12.criptografarSenha();
		usuario12.setNomeFoto("12.png");
		usuario12 = usuarioRepository.save(usuario12);
		users.add(usuario12);
		
		Usuario usuario13 = new Usuario();
		usuario13.setNome("Silvio Torres Costa");
		usuario13.setUsuario("silvio");
		usuario13.setSenha("a");
		usuario13.criptografarSenha();
		usuario13.setNomeFoto("13.png");
		usuario13 = usuarioRepository.save(usuario13);
		users.add(usuario13);
		
		Usuario usuario14 = new Usuario();
		usuario14.setNome("Regina Silva Garcia");
		usuario14.setUsuario("regina");
		usuario14.setSenha("a");
		usuario14.criptografarSenha();
		usuario14.setNomeFoto("14.png");
		usuario14 = usuarioRepository.save(usuario14);
		users.add(usuario14);
		
		Usuario usuario15 = new Usuario();
		usuario15.setNome("Mariano Lopez Fernandez");
		usuario15.setUsuario("mariano");
		usuario15.setSenha("a");
		usuario15.criptografarSenha();
		usuario15.setNomeFoto("15.png");
		usuario15 = usuarioRepository.save(usuario15);
		users.add(usuario15);
		
		Usuario usuario16 = new Usuario();
		usuario16.setNome("Vanessa Medeiros Lima");
		usuario16.setUsuario("vanessa");
		usuario16.setSenha("a");
		usuario16.criptografarSenha();
		usuario16.setNomeFoto("16.png");
		usuario16 = usuarioRepository.save(usuario16);
		users.add(usuario16);
		
		Usuario usuario17 = new Usuario();
		usuario17.setNome("Michael Junior Lima");
		usuario17.setUsuario("michael");
		usuario17.setSenha("a");
		usuario17.criptografarSenha();
		usuario17.setNomeFoto("17.png");
		usuario17 = usuarioRepository.save(usuario17);
		users.add(usuario17);
		
		Usuario usuario18 = new Usuario();
		usuario18.setNome("Renata Braz Silva");
		usuario18.setUsuario("renata");
		usuario18.setSenha("a");
		usuario18.criptografarSenha();
		usuario18.setNomeFoto("18.png");
		usuario18 = usuarioRepository.save(usuario18);
		users.add(usuario18);
		
		Usuario usuario19 = new Usuario();
		usuario19.setNome("Vinicius Costa Ferraz");
		usuario19.setUsuario("vinicius");
		usuario19.setSenha("a");
		usuario19.criptografarSenha();
		usuario19.setNomeFoto("19.png");
		usuario19 = usuarioRepository.save(usuario19);
		users.add(usuario19);
		
		Usuario usuario20 = new Usuario();
		usuario20.setNome("Pricila Siqueira Ramos");
		usuario20.setUsuario("priscila");
		usuario20.setSenha("a");
		usuario20.criptografarSenha();
		usuario20.setNomeFoto("20.png");
		usuario20 = usuarioRepository.save(usuario20);
		users.add(usuario20);
		
		return users;
	}
}
