package com.perisatto.fiapprj.menuguru.application.domain.model;

public class Customer {	
	private String name = null;
	private String email = null;
	private final CPF documentNumber;


	public Customer(CPF documentNumber, String customerName, String customerEmail) {
		this.documentNumber = documentNumber;
		this.name = customerName;
		this.email = customerEmail;
	}


	public Customer(CPF cpf) {
		this.documentNumber = null;
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


	public CPF getDocumentNumber() {
		return documentNumber;
	}


	public boolean create() {
		return false;		
	}
}
