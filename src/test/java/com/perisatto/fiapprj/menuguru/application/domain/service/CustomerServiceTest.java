package com.perisatto.fiapprj.menuguru.application.domain.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import com.perisatto.fiapprj.menuguru.application.domain.model.Customer;

public class CustomerServiceTest {

//	@Test
//	void givenValidCPF_thenRegisterCustomer() throws Exception {
//		
//		String customerName = "Roberto Machado";
//		String customerEmail = "roberto.machado@bestmail.com";
//		String documentNumber = "90779778057";
//		
//		CustomerService newCustomer = new CustomerService();
//		
//		Customer customer = newCustomer.createCustomer(documentNumber, customerName, customerEmail);
//		
//		assertThat(customer.getId()).isPositive();
//	}
//	
//	@Test
//	void givenInvalidCPF_thenRefusesToRegisterCustomer() throws Exception {
//		
//		String customerName = "Roberto Machado";
//		String customerEmail = "roberto.machado@bestmail.com";
//		String documentNumber = "90779778058";
//		
//		CustomerService newCustomer = new CustomerService();
//		
//		Customer customer = newCustomer.createCustomer(documentNumber, customerName, customerEmail);
//		
//		assertThat(customer.getId()).isNull();
//	}
//	
//	@Test
//	void givenInvalidEmail_thenRefusesToRegisterCustomer() throws Exception {
//		
//		String customerName = "Roberto Machado";
//		String customerEmail = "roberto.machadobestmail.com";
//		String documentNumber = "90779778057";
//		
//		CustomerService newCustomer = new CustomerService();
//		
//		Customer customer = newCustomer.createCustomer(documentNumber, customerName, customerEmail);
//		
//		assertThat(customer.getId()).isNull();
//	}
//	
//	@Test
//	void givenEmptyName_thenRefusesToRegisterCustomer() throws Exception {
//		
//		String customerName = "";
//		String customerEmail = "roberto.machadobestmail.com";
//		String documentNumber = "90779778057";
//		
//		CustomerService newCustomer = new CustomerService();
//		
//		Customer customer = newCustomer.createCustomer(documentNumber, customerName, customerEmail);
//		
//		assertThat(customer.getId()).isNull();
//	}
//	
//	@Test
//	void givenCPF_thenGetCustomerData() throws Exception {
//		
//		String documentNumber = "90779778057";
//		
//		CustomerService customerQuery = new CustomerService();
//		
//		Customer customer = customerQuery.getCustomerByCPF(documentNumber);
//		
//		assertThat(customer.getDocumentNumber().getDocumentNumber()).isEqualTo(documentNumber);
//	}
}
