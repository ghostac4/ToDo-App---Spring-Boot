package com.todonotes.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class AuthenticationException extends RuntimeException {

	private static final long serialVersionUID = -1449691983626780917L;

	public AuthenticationException() {
		super();
	}

	public AuthenticationException(String message) {
		super(message);
	}

	public AuthenticationException(String message, Throwable throwable) {
		super(message, throwable);
	}

	public AuthenticationException(Throwable throwable) {
		super(throwable);
	}
}
