package com.hire.domain.exception;

public class NotBlankException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public NotBlankException(String msg) {
		super(msg);
	}
}
