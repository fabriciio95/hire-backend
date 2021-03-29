package com.hire.api.exceptionhandler;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.hire.api.exceptionhandler.Problema.Campo;
import com.hire.domain.exception.ArquivoException;
import com.hire.domain.exception.AvaliacaoException;
import com.hire.domain.exception.NotBlankException;
import com.hire.domain.exception.UsuarioJaCadastradoException;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {
	
	@Autowired
	private MessageSource messageSource;
	
	
	@ExceptionHandler(UsuarioJaCadastradoException.class)
	public ResponseEntity<Object> HandleUsuarioJaCadastrado(UsuarioJaCadastradoException ex, WebRequest request) {
		var status = HttpStatus.NOT_FOUND;
		var problema = criarProblema(status.value(), ex.getMessage());
		ex.printStackTrace();
		return super.handleExceptionInternal(ex, problema, new HttpHeaders(), status, request);
	}
	
	@ExceptionHandler(NoSuchElementException.class)
	public ResponseEntity<Object> HandleNoSuchElement(NoSuchElementException ex, WebRequest request) {
		var status = HttpStatus.NOT_FOUND;
		var problema = criarProblema(status.value(), "Elemento não encontrado");
		ex.printStackTrace();
		return super.handleExceptionInternal(ex, problema, new HttpHeaders(), status, request); 
	}
	
	@ExceptionHandler(ArquivoException.class)
	public ResponseEntity<Object> HandleException(ArquivoException ex, WebRequest request) {
		var status = HttpStatus.INTERNAL_SERVER_ERROR;
		var problema = criarProblema(status.value(), ex.getMessage());
		ex.printStackTrace();
		return super.handleExceptionInternal(ex, problema, new HttpHeaders(), status, request);
	}
	
	@ExceptionHandler(NotBlankException.class)
	public ResponseEntity<Object> HandleNotBlankException(NotBlankException ex, WebRequest request) {
		var status = HttpStatus.BAD_REQUEST;
		var problema = criarProblema(status.value(), ex.getMessage());
		ex.printStackTrace();
		return super.handleExceptionInternal(ex, problema, new HttpHeaders(), status, request);
	}
	
	@ExceptionHandler(AvaliacaoException.class)
	public ResponseEntity<Object> AvaliacaoException(AvaliacaoException ex, WebRequest request) {
		var status = HttpStatus.BAD_REQUEST;
		var problema = criarProblema(status.value(), ex.getMessage());
		ex.printStackTrace();
		return super.handleExceptionInternal(ex, problema, new HttpHeaders(), status, request);
	}
	
	
	
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		var campos = new ArrayList<Problema.Campo>();
		for(ObjectError error : ex.getBindingResult().getAllErrors()) {
			String nome = ((FieldError) error).getField();
			String mensagem = messageSource.getMessage(error, LocaleContextHolder.getLocale());
			campos.add(new Campo(nome, mensagem));
		}
		Problema problema = criarProblema(status.value(), "Um ou mais campos inválidos. Faça o preenchimento correto e tente novamente!");
		problema.setCampos(campos);
		ex.printStackTrace();
		return super.handleExceptionInternal(ex, problema, headers, status, request);
	}
	
	
	
	private Problema criarProblema(Integer status, String titulo) {
		var problema = new Problema();
		problema.setStatus(status);
		problema.setTitulo(titulo);
		problema.setDataHora(OffsetDateTime.now());
		return problema;
	}

}
