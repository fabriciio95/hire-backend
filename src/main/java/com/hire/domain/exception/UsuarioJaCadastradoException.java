package com.hire.domain.exception;

public class UsuarioJaCadastradoException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public UsuarioJaCadastradoException(String msg) {
		super(msg);
	}
}
