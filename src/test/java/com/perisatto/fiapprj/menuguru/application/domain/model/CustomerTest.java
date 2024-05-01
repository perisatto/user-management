package com.perisatto.fiapprj.menuguru.application.domain.model;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;

import com.perisatto.fiapprj.menuguru.application.domain.model.CPF;
import com.perisatto.fiapprj.menuguru.application.domain.model.Customer;

public class CustomerTest {

	@Test
	void givenValidCPF_thenRegisterCustomer() throws Exception {

		String customerName = "Roberto Machado";
		String customerEmail = "roberto.machado@bestmail.com";
		String documentNumber = "90779778057";

		CPF customerCPF = new CPF(documentNumber);
		Customer customer = new Customer(customerCPF, customerName, customerEmail);

		assertThat(customer.getName()).isEqualTo(customerName);
		assertThat(customer.getEmail()).isEqualTo(customerEmail);
		assertThat(customer.getDocumentNumber().getDocumentNumber()).isEqualTo(customerCPF.getDocumentNumber());
	}

	@Test
	void givenInvalidCPF_thenRefusesToRegisterCustomer() throws Exception {
		try {
			String customerName = "Roberto Machado";
			String customerEmail = "roberto.machado@bestmail.com";
			String documentNumber = "90779778058";

			CPF customerCPF = new CPF(documentNumber);
			Customer customer = new Customer(customerCPF, customerName, customerEmail);

			assertThat(customer.getName()).isNull();
			assertThat(customer.getEmail()).isNull();
			assertThat(customer.getDocumentNumber()).isNull();
		}catch (Exception e) {
			assertThat(e.getMessage()).isEqualTo("CPF inválido");
		}
	}

	@Test
	void givenInvalidEmail_thenRefusesToRegisterCustomer() throws Exception {

		try { 
			String customerName = "Roberto Machado";
			String customerEmail = "roberto.machadobestmail.com";
			String documentNumber = "90779778057";

			CPF customerCPF = new CPF(documentNumber);
			Customer customer = new Customer(customerCPF, customerName, customerEmail);

			assertThat(customer.getName()).isEqualTo(customerName);
			assertThat(customer.getEmail()).isEqualTo(customerEmail);
			assertThat(customer.getDocumentNumber().getDocumentNumber()).isEqualTo(customerCPF.getDocumentNumber());
			assertThat(customer.getName()).isNull();
			assertThat(customer.getEmail()).isNull();
			assertThat(customer.getDocumentNumber()).isNull();
		}catch (Exception e) {
			assertThat(e.getMessage()).isEqualTo("E-mail inválido");
		}
	}
	
	@Test
	void givenEmptyName_thenRefusesToRegisterCustomer() throws Exception {

		try { 
			String customerName = "";
			String customerEmail = "roberto.machadobestmail.com";
			String documentNumber = "90779778057";

			CPF customerCPF = new CPF(documentNumber);
			Customer customer = new Customer(customerCPF, customerName, customerEmail);

			assertThat(customer.getName()).isEqualTo(customerName);
			assertThat(customer.getEmail()).isEqualTo(customerEmail);
			assertThat(customer.getDocumentNumber().getDocumentNumber()).isEqualTo(customerCPF.getDocumentNumber());
			assertThat(customer.getName()).isNull();
			assertThat(customer.getEmail()).isNull();
			assertThat(customer.getDocumentNumber()).isNull();
		}catch (Exception e) {
			assertThat(e.getMessage()).isEqualTo("Nome não pode ser vazio");
		}
	}
}
