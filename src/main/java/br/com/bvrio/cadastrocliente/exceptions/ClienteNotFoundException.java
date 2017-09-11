package br.com.bvrio.cadastrocliente.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="Recurso não encontrado")
public class ClienteNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public ClienteNotFoundException(String message) {
		super(message);
	}


}
