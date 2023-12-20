package com.main.exceptions;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String,String>> methodArgumentNotValidException(MethodArgumentNotValidException ex ){
		Map<String,String> errors = new HashMap<>();

		ex.getFieldErrors().forEach(error -> {
			errors.put(error.getField(), error.getDefaultMessage());
		});

		return  ResponseEntity.badRequest().body(errors);
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<Map<String,String>> constraintViolationException(ConstraintViolationException ex ){
		Map<String,String> errors = new HashMap<>();

		ex.getConstraintViolations().forEach(constraintViolation -> {
			errors.put(constraintViolation.getPropertyPath().toString(), constraintViolation.getMessage());
		});

		return  ResponseEntity.badRequest().body(errors);
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<ErrorResponse> dataIntegrityViolationException(DataIntegrityViolationException ex , WebRequest request){
		return  ResponseEntity.badRequest().body(new ErrorResponse("There is problem with database!", LocalDateTime.now(), request.getDescription(false)));
	}

	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<ErrorResponse> badCredentialsException(BadCredentialsException ex , WebRequest req){
		return ResponseEntity.badRequest().body(new ErrorResponse(ex.getMessage(), LocalDateTime.now(), req.getDescription(false)));
	}

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorResponse> resourceNotFoundException(ResourceNotFoundException ex , WebRequest req){
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(ex.getMessage(), LocalDateTime.now(), req.getDescription(false)));
	}

	@ExceptionHandler(UserException.class)
	public ResponseEntity<ErrorResponse> userException(UserException ex , WebRequest req){
		return ResponseEntity.badRequest().body(new ErrorResponse(ex.getMessage(), LocalDateTime.now(), req.getDescription(false)));
	}
	
	@ExceptionHandler(ProductException.class)
	public ResponseEntity<ErrorResponse> productException(ProductException ex , WebRequest req){
		return ResponseEntity.badRequest().body(new ErrorResponse(ex.getMessage(), LocalDateTime.now(), req.getDescription(false)));
	}
	
	@ExceptionHandler(CategoryException.class)
	public ResponseEntity<ErrorResponse> categoryException(CategoryException ex , WebRequest req){
		return ResponseEntity.badRequest().body(new ErrorResponse(ex.getMessage(), LocalDateTime.now(), req.getDescription(false)));
	}

	@ExceptionHandler(OrderException.class)
	public ResponseEntity<ErrorResponse> orderException(OrderException ex , WebRequest req){
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(ex.getMessage(), LocalDateTime.now(), req.getDescription(false)));
	}
}
