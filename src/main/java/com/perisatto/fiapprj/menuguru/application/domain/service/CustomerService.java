package com.perisatto.fiapprj.menuguru.application.domain.service;

import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.perisatto.fiapprj.menuguru.application.domain.model.CPF;
import com.perisatto.fiapprj.menuguru.application.domain.model.Customer;
import com.perisatto.fiapprj.menuguru.application.port.in.ManageCustomerUseCase;
import com.perisatto.fiapprj.menuguru.application.port.out.CreateCustomerPort;
import com.perisatto.fiapprj.menuguru.application.port.out.LoadCustomerPort;
import com.perisatto.fiapprj.menuguru.handler.exceptions.CustomerNotFoundException;
import com.perisatto.fiapprj.menuguru.handler.exceptions.CustomerValidationException;

public class CustomerService implements ManageCustomerUseCase {

	static final Logger logger = LogManager.getLogger(CustomerService.class);

	private CreateCustomerPort createCustomerPort;
	private final LoadCustomerPort loadCustomerPort;

	public CustomerService(LoadCustomerPort loadCustomerPort, CreateCustomerPort createCustomerPort) {
		this.loadCustomerPort = loadCustomerPort;
		this.createCustomerPort = createCustomerPort;
	}

	public Customer createCustomer(String documentNumber, String name, String email) throws Exception {
		logger.info("Asked for new customer register...");
		Customer newCustomer;

		CPF newCustomerDocumentNumber = new CPF(documentNumber);
		String newCustomerName = name;
		String newCustomerEmail = email;

		newCustomer = new Customer(null, newCustomerDocumentNumber, newCustomerName, newCustomerEmail);

		if(loadCustomerPort.getCustomerByCPF(newCustomer.getDocumentNumber()).isEmpty()) {
			logger.info("Customer inexistent... creating new customer register.");
			createCustomerPort.createCustomer(newCustomer);
			logger.info("New customer register created.");
			return newCustomer;
		}else {
			logger.info("Customer already exists. New customer register aborted.");
			throw new CustomerValidationException("cstm-1001", "Customer already exists");
		}	
	}

	public Customer getCustomerByCPF(String documentNumber) throws Exception {
		CPF customerDocument;
		customerDocument = new CPF(documentNumber);			
		Optional<Customer> customer = loadCustomerPort.getCustomerByCPF(customerDocument);
		if(customer.isPresent()) {
			return customer.get();
		} else {
			throw new CustomerNotFoundException("cstm-1003", "Customer not found");
		}	
	}	
}
