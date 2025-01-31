package com.perisatto.fiapprj.user_management.handler.exceptions;

public class NotFoundException extends Exception {
	private static final long serialVersionUID = 1L;
	
	private String errorCode;
	
	public NotFoundException(String errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
	}

	public String getErrorCode() {
		return errorCode;
	}
}
