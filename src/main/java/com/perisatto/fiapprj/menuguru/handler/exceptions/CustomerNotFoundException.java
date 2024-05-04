package com.perisatto.fiapprj.menuguru.handler.exceptions;

public class CustomerNotFoundException extends Exception {
	private static final long serialVersionUID = 1L;
	
	private String errorCode;
	
	public CustomerNotFoundException(String errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
	}

	public String getErrorCode() {
		return errorCode;
	}
}
