package com.hire.domain.utils;

import org.springframework.beans.factory.annotation.Autowired;

import com.hire.api.model.UsuarioProfissionalApi;
import com.hire.api.model.UsuarioProfissionalApi.EuQuero;
import com.hire.domain.model.Profissional;
import com.hire.domain.model.Usuario;
import com.hire.domain.repository.UsuarioRepository;

public class UsuarioUtils {
	
	@Autowired
	public UsuarioRepository usuarioRepository;
	
	public static Usuario fromUsuarioAPIForUsuario(UsuarioProfissionalApi usuarioApi) {
		Usuario usuario = new Usuario();
		usuario.setId(usuarioApi.getId());
		usuario.setNome(usuarioApi.getNome());
		usuario.setUsuario(usuarioApi.getUsuario());
		usuario.setSenha(usuarioApi.getSenha());
		usuario.setFotoBase64(usuarioApi.getFotoBase64());
		return usuario;
	}
	
	
	
	public static  Profissional fromUsuarioApiForProfissional(UsuarioProfissionalApi usuarioApi) {
		Profissional profissional = new Profissional();
		profissional.setDescricao(usuarioApi.getDescricao());
		profissional.setEmail(usuarioApi.getEmail());
		profissional.setEndereco(usuarioApi.getEndereco());
		profissional.setTelefone(usuarioApi.getTelefone());
		profissional.setValorHora(usuarioApi.getValorHora());
		return profissional;
	}
	
	public static  UsuarioProfissionalApi fromUsuarioAndProfissionalForUsuarioApi(Usuario usuario, Profissional profissional) {
		UsuarioProfissionalApi usuarioApi = new UsuarioProfissionalApi();
		usuarioApi.setId(usuario.getId());
		usuarioApi.setNome(usuario.getNome());
		usuarioApi.setUsuario(usuario.getUsuario());
		usuarioApi.setSenha(usuario.getSenha());
		usuarioApi.setFotoBase64(usuario.getFotoBase64());
		usuarioApi.setDescricao(profissional.getDescricao());
		usuarioApi.setEmail(profissional.getEmail());
		usuarioApi.setEndereco(profissional.getEndereco());
		usuarioApi.setTelefone(profissional.getTelefone());
		usuarioApi.setValorHora(profissional.getValorHora());
		if(usuarioApi.getDescricao() != null) {
			usuarioApi.setEuQuero(EuQuero.AMBOS);
		} else {
			usuarioApi.setEuQuero(EuQuero.CONTRATAR);
		}
		return usuarioApi;
	}
	
	public static UsuarioProfissionalApi fromUsuarioForUsuarioProfissionalApi(Usuario usuario) {
		UsuarioProfissionalApi usuarioApi = new UsuarioProfissionalApi();
		usuarioApi.setId(usuario.getId());
		usuarioApi.setNome(usuario.getNome());
		usuarioApi.setUsuario(usuario.getUsuario());
		usuarioApi.setFotoBase64(usuario.getFotoBase64());
		usuarioApi.setSenha(usuario.getSenha());
		return usuarioApi;
	}

}
