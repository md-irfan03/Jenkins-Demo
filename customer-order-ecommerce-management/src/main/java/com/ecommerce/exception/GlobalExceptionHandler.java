package com.ecommerce.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler
{

	@ExceptionHandler({OrderNotExistException.class, CustomerNotExistException.class })
	public ResponseEntity<ExceptionResponse> handleCommonExceptions(Exception e){
		System.out.println("GlobalExceptionHandler.handleException()");
		
		ExceptionResponse exceptionResponse = new ExceptionResponse();
		exceptionResponse.setErrorCode("404-NOT FOUND");
		exceptionResponse.setMessage(e.getMessage());
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exceptionResponse);
	}
	
	@ExceptionHandler(CustomerAlreadyExistsException.class)
	public ResponseEntity<ExceptionResponse> handleCustomerExistsException(CustomerAlreadyExistsException e){
		ExceptionResponse exceptionResponse = new ExceptionResponse();
		
		exceptionResponse.setErrorCode("409-CONFLICT");
		exceptionResponse.setMessage(e.getMessage());
		
		return ResponseEntity.status(HttpStatus.CONFLICT).body(exceptionResponse);
	}
}
