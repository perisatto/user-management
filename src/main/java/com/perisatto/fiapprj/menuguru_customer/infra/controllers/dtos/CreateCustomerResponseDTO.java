package com.perisatto.fiapprj.menuguru_customer.infra.controllers.dtos;

import com.fasterxml.jackson.annotation.JsonAlias;

public class CreateCustomerResponseDTO {
	private Long id;
	private String documentNumber;
	private String name;
	
	@JsonAlias(value = "email")
	private String email;
	
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	public String getDocumentNumber() {
		return documentNumber;
	}
	public void setDocumentNumber(String documentNumber) {
		this.documentNumber = documentNumber;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
}
