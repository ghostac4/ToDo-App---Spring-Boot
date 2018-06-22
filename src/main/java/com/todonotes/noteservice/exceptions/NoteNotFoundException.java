package com.todonotes.noteservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NoteNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 221679622947400638L;

	public NoteNotFoundException() {
		super();
	}

	public NoteNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public NoteNotFoundException(String message) {
		super(message);
	}

	public NoteNotFoundException(Throwable throwable) {
		super(throwable);
	}
}
