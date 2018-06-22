package com.todonotes.exceptions;

import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import io.jsonwebtoken.ExpiredJwtException;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler{

	private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	
	@ExceptionHandler(value=SQLException.class)
	public ResponseEntity<?> handleSqlException(RuntimeException ex, WebRequest request) {
		LOGGER.error(ex.getMessage());
		String responseBody ="Sql Exception";
		return handleExceptionInternal(ex, responseBody, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
	}
	
	@ExceptionHandler(value=NullPointerException.class)
	public ResponseEntity<?> handleNullPointerException(RuntimeException runtimeException){
		LOGGER.error(runtimeException.getMessage());
		return new ResponseEntity<>("Null pointer Exception",HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(value=ExpiredJwtException.class)
	public ResponseEntity<?> handleJWTException(RuntimeException ex){
		LOGGER.error(ex.getMessage());
		return new ResponseEntity<>("Token Expired",HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(value=RuntimeException.class)
	public ResponseEntity<?> handleException(RuntimeException runtimeException){
		LOGGER.error(runtimeException.getMessage());
		return new ResponseEntity<>("Runtime Exception",HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
