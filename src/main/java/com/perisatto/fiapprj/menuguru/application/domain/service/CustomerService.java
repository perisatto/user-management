package com.perisatto.fiapprj.menuguru.application.domain.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.perisatto.fiapprj.menuguru.application.domain.model.CPF;
import com.perisatto.fiapprj.menuguru.application.domain.model.Customer;
import com.perisatto.fiapprj.menuguru.application.port.in.CustomerInterface;

public class CustomerService implements CustomerInterface {

	static final Logger logger = LogManager.getLogger(CustomerService.class);

	@Override
	public boolean createCustomer(String documentNumber, String name, String email) {
		try {
			CPF cpf;
			cpf = new CPF(documentNumber);
			Customer customer = new Customer(cpf, name, email);			
			return customer.create();
		} catch (Exception e) {
			logger.error("Error creating new customer: " + e.getMessage());
			return false;
		} 
	}

	@Override
	public Customer getCustomerByCPF(String documentNumber) {
		try { 
			CPF cpf = new CPF(documentNumber);
			Customer customer = new Customer(cpf);
			return customer;
		}catch (Exception e) {
			logger.error("Error getting customer: " + e.getMessage());
			return null;
		}
	}	
}
