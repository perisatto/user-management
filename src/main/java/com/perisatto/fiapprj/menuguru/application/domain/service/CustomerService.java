package com.perisatto.fiapprj.menuguru.application.domain.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.perisatto.fiapprj.menuguru.application.domain.model.CPF;
import com.perisatto.fiapprj.menuguru.application.domain.model.Customer;
import com.perisatto.fiapprj.menuguru.application.port.in.CreateCustomerUseCase;
import com.perisatto.fiapprj.menuguru.application.port.out.CreateCustomerPort;
import com.perisatto.fiapprj.menuguru.application.port.out.LoadCustomerPort;

public class CustomerService implements CreateCustomerUseCase {

	static final Logger logger = LogManager.getLogger(CustomerService.class);
	
	private CreateCustomerPort createCustomerPort;
	private final LoadCustomerPort loadCustomerPort;

	public CustomerService(LoadCustomerPort loadCustomerPort) {
		this.loadCustomerPort = loadCustomerPort;
	}
	
	public Customer createCustomer(String documentNumber, String name, String email) {

		Customer newCustomer;

		try {
			CPF newCustomerDocumentNumber = new CPF(documentNumber);
			String newCustomerName = name;
			String newCustomerEmail = email;

			newCustomer = new Customer(newCustomerDocumentNumber, newCustomerName, newCustomerEmail);

			if(loadCustomerPort.getCustomerByCPF(newCustomer.getDocumentNumber()).isEmpty()) {
				createCustomerPort.createCustomer(newCustomer);
			}

		}catch (Exception e) {
			return null;
		}

		return newCustomer;
	}

	public Customer getCustomerByCPF(String documentNumber) {
		return null;
	}	
}
