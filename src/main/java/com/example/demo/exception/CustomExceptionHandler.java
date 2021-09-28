package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class CustomExceptionHandler {
	@ExceptionHandler(NotFoundException.class)
	public ErrorResponse handlerNotFoundException(NotFoundException ex, WebRequest req) {

		return new ErrorResponse(ex.getMessage(),HttpStatus.NOT_FOUND);
	}
}
