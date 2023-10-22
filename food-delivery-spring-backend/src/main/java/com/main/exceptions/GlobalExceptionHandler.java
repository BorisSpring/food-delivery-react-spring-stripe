package com.main.exceptions;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<ErrorResponse> badCredentialsException(BadCredentialsException ex , WebRequest req){
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(ex.getMessage(), LocalDateTime.now(), req.getDescription(false)));
	}
	
	
	@ExceptionHandler(UserException.class)
	public ResponseEntity<ErrorResponse> userException(UserException ex , WebRequest req){
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(ex.getMessage(), LocalDateTime.now(), req.getDescription(false)));
	}
	
	@ExceptionHandler(ProductException.class)
	public ResponseEntity<ErrorResponse> productException(ProductException ex , WebRequest req){
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(ex.getMessage(), LocalDateTime.now(), req.getDescription(false)));
	}
	
	@ExceptionHandler(CategoryException.class)
	public ResponseEntity<ErrorResponse> categoryException(CategoryException ex , WebRequest req){
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(ex.getMessage(), LocalDateTime.now(), req.getDescription(false)));
	}
	
	
	@ExceptionHandler(OrderException.class)
	public ResponseEntity<ErrorResponse> orderException(OrderException ex , WebRequest req){
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(ex.getMessage(), LocalDateTime.now(), req.getDescription(false)));
	}
}
