package com.perisatto.fiapprj.menuguru.application.domain.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import com.perisatto.fiapprj.menuguru.adapter.out.CustomerJpaEntity;
import com.perisatto.fiapprj.menuguru.application.domain.model.CPF;
import com.perisatto.fiapprj.menuguru.application.domain.model.Customer;
import com.perisatto.fiapprj.menuguru.application.port.out.CreateCustomerPort;
import com.perisatto.fiapprj.menuguru.application.port.out.LoadCustomerPort;
import com.perisatto.fiapprj.menuguru.handler.exceptions.CustomerNotFoundException;
import com.perisatto.fiapprj.menuguru.handler.exceptions.CustomerValidationException;

public class CustomerServiceTest {

	private final LoadCustomerPort loadCustomerPort = new LoadCustomerPort() {

		@Override
		public Optional<CustomerJpaEntity> loadCustomer(Long customerId) {
			CustomerJpaEntity customerJpaEntity = new CustomerJpaEntity();
			customerJpaEntity.setIdCustomer(customerId);
			customerJpaEntity.setDocumentNumber("90779778057");
			customerJpaEntity.setName("Roberto Machado");
			customerJpaEntity.seteMail("roberto.machado@bestmail.com");
			return Optional.of(customerJpaEntity);
		}

		@Override
		public Optional<Customer> getCustomerByCPF(CPF customerDocument) {						
			try {
				CPF documentNumber = new CPF("90779778057");
				if(documentNumber.getDocumentNumber() == customerDocument.getDocumentNumber()) {					
					Customer customer = new Customer(10L, documentNumber, "Roberto Machado", "roberto.machado@bestmail.com");
					return Optional.of(customer);
				} else {
					return Optional.empty();
				}
			} catch (Exception e) {
				return Optional.empty();
			}
		}
	};

	private final CreateCustomerPort createCustomerPort = new CreateCustomerPort() {

		@Override
		public Customer createCustomer(Customer customer) {
			try {
				Customer newCustomer = new Customer(10L, customer.getDocumentNumber(), customer.getName(), customer.getEmail());
				return newCustomer;
			} catch (Exception e) {
				return null;
			}
		}
	};

	@Test
	void givenValidCPF_thenRegisterCustomer() throws Exception {		
		String customerName = "Roberto Machado";
		String customerEmail = "roberto.machado@bestmail.com";
		String documentNumber = "35732996010";

		CustomerService newCustomerService = new CustomerService(loadCustomerPort, createCustomerPort);

		Customer customer = newCustomerService.createCustomer(documentNumber, customerName, customerEmail);

		assertThat(customer.getDocumentNumber().getDocumentNumber()).isEqualTo(documentNumber);
		assertThat(customer.getName()).isEqualTo(customerName);
		assertThat(customer.getEmail()).isEqualTo(customerEmail);
	}

	@Test
	void givenInvalidCPF_thenRefusesToRegisterCustomer() {

		String customerName = "Roberto Machado";
		String customerEmail = "roberto.machado@bestmail.com";
		String documentNumber = "90779778058";

		CustomerService newCustomerService = new CustomerService(loadCustomerPort, createCustomerPort);


		try {
			Customer customer = newCustomerService.createCustomer(documentNumber, customerName, customerEmail);
		} catch (CustomerValidationException e) {
			assertThat(e.getMessage()).isEqualTo("invalid document number");
		} catch (Exception e) {
			assertThat(e.getMessage()).isNotEqualTo("invalid document number");
		}
	}

	@Test
	void givenInvalidEmail_thenRefusesToRegisterCustomer() {

		String customerName = "Roberto Machado";
		String customerEmail = "roberto.machadobestmail.com";
		String documentNumber = "90779778057";

		CustomerService newCustomerService = new CustomerService(loadCustomerPort, createCustomerPort);

		try {
			Customer customer = newCustomerService.createCustomer(documentNumber, customerName, customerEmail);
		} catch (CustomerValidationException e) {
			assertThat(e.getMessage()).contains("invalid e-mail format");
		} catch (Exception e) {
			assertThat(e.getMessage()).doesNotContain("invalid e-mail format");
		}
	}

	@Test
	void givenEmptyName_thenRefusesToRegisterCustomer() throws Exception {

		String customerName = "";
		String customerEmail = "roberto.machadobestmail.com";
		String documentNumber = "90779778057";

		CustomerService newCustomerService = new CustomerService(loadCustomerPort, createCustomerPort);

		try {
			Customer customer = newCustomerService.createCustomer(documentNumber, customerName, customerEmail);
		} catch (CustomerValidationException e) {
			assertThat(e.getMessage()).contains("empty, null or blank name");
		} catch (Exception e) {
			assertThat(e.getMessage()).doesNotContain("empty, null or blank name");
		}
	}

	@Test
	void givenCPF_thenGetCustomerData() {
		try {
			String documentNumber = "90779778057";

			CustomerService newCustomerService = new CustomerService(loadCustomerPort, createCustomerPort);

			Customer customer = newCustomerService.getCustomerByCPF(documentNumber);

			assertThat(customer.getDocumentNumber().getDocumentNumber()).isEqualTo(documentNumber);
		} catch (CustomerNotFoundException e) {
			assertThat(e.getMessage()).doesNotContain("Customer not found");
		} catch (Exception e) {
			assertThat(e.getMessage()).doesNotContain("Customer not found");
		}		
	}

	@Test
	void givenInexistentCPF_thenGetNotFound () {
		try {
			String documentNumber = "35732996010";

			CustomerService newCustomerService = new CustomerService(loadCustomerPort, createCustomerPort);

			Customer customer = newCustomerService.getCustomerByCPF(documentNumber);

			assertThat(customer.getName()).isNullOrEmpty();
		} catch (CustomerNotFoundException e) {
			assertThat(e.getMessage()).isEqualTo("Customer not found");
		} catch (Exception e) {
			assertThat(e.getMessage()).doesNotContain("Customer not found");
		}

	}
}
