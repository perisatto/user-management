package com.perisatto.fiapprj.menuguru.handler.errors;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

public class HttpErrorHandler {
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
	private LocalDateTime timestamp;
	private String error;
	private String message;
	private String detail;
	//TODO Criar informações de ajuda para cada erro.
	private String help = "about:blank";

	private HttpErrorHandler() {
		timestamp = LocalDateTime.now();
	}
	
	public HttpErrorHandler(String error, String message, String detail) {
		this();
		this.error = error;
		this.message = message;
		this.detail = detail;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public String getHelp() {
		return help;
	}
	
	
}
