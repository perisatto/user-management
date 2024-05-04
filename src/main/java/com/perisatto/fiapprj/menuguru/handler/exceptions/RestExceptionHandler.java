package com.perisatto.fiapprj.menuguru.handler.exceptions;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.perisatto.fiapprj.menuguru.handler.errors.HttpErrorHandler;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(CustomerValidationException.class)
	protected ResponseEntity<Object> handleRequestSchemaValidation(CustomerValidationException ex) {
		String message = "Request schema validation error";
		String erroCode = ex.getErrorCode();
		return buildResponseEntity(new HttpErrorHandler(erroCode, message, ex.getMessage()), HttpStatus.UNPROCESSABLE_ENTITY);
	}
	
	@ExceptionHandler(CustomerNotFoundException.class)
	protected ResponseEntity<Object> handleResourceNotFound(CustomerNotFoundException ex) {
		String message = "Resource not found";
		String erroCode = ex.getErrorCode();
		return buildResponseEntity(new HttpErrorHandler(erroCode, message, ex.getMessage()), HttpStatus.NOT_FOUND);
	}

	private ResponseEntity<Object> buildResponseEntity(HttpErrorHandler apiError, HttpStatus httpStatus) {
		logger.debug("Finished with exception: " + apiError.getError());
		return new ResponseEntity<>(apiError, httpStatus);
	}

}
