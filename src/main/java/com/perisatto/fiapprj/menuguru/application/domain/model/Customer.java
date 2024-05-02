package com.perisatto.fiapprj.menuguru.application.domain.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Customer {	
	
	static final Logger logger = LogManager.getLogger(Customer.class);
	
	private Integer id;
	private String name = null;
	private String email = null;
	private final CPF documentNumber;

	public Customer(CPF documentNumber, String customerName, String customerEmail) throws Exception {
		if(!validate(customerName, customerEmail)) {
			Exception error = new Exception("Campos inválidos");
			throw error;
		}
		
		this.documentNumber = documentNumber;
		this.name = customerName;
		this.email = customerEmail;	
	}


	private boolean validate(String customerName, String customerEmail) {			
		if((customerName == null) || (customerName.isEmpty()) || (customerName.isBlank())) {
			logger.debug("Error validating customer date: empty, null or blank name");
			System.out.println("nome:" + customerName);
			return false;			
		}

		if((customerEmail == null) || (customerEmail.isEmpty()) || (customerEmail.isBlank())) {
			logger.debug("Error validating customer date: empty, null or blank e-mail");
			System.out.println("email " + customerEmail);
			return false;			
		} else if(!customerEmail.matches("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")) {
			logger.debug("Error validating customer date: invalid e-mail format");
			System.out.println("email format " + customerEmail);
			return false;
		}
		
		return true;
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


	public Integer getId() {
		return id;
	}
}
