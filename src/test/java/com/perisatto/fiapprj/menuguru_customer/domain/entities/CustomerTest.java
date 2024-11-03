package com.perisatto.fiapprj.menuguru_customer.domain.entities;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import com.perisatto.fiapprj.menuguru_customer.domain.entities.customer.CPF;
import com.perisatto.fiapprj.menuguru_customer.domain.entities.customer.Customer;

@ActiveProfiles(value = "test")
public class CustomerTest {

	@Test
	void givenValidCPF_thenRegisterCustomer() throws Exception {
		String customerName = "Roberto Machado";
		String customerEmail = "roberto.machado@bestmail.com";
		String documentNumber = "90779778057";

		CPF customerCPF = new CPF(documentNumber);
		Customer customer = new Customer(null, customerCPF, customerName, customerEmail);

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
			Customer customer = new Customer(null, customerCPF, customerName, customerEmail);

			assertThat(customer.getName()).isNull();
			assertThat(customer.getEmail()).isNull();
			assertThat(customer.getDocumentNumber()).isNull();
		}catch (Exception e) {
			assertThat(e.getMessage()).isEqualTo("Invalid document number");
		}
	}

	@Test
	void givenSequencialCharCPF_thenRefusesToRegisterCustomer() throws Exception {
		String customerName = "Roberto Machado";
		String customerEmail = "roberto.machado@bestmail.com";
		String[] documentNumber = {"00000000000","11111111111","22222222222","33333333333","44444444444",
				"55555555555","66666666666","77777777777","88888888888","99999999999","012346789"};

		for (String string : documentNumber) {
			try {
				CPF customerCPF = new CPF(string);
				Customer customer = new Customer(null, customerCPF, customerName, customerEmail);
				assertThat(customer.getName()).isNull();
				assertThat(customer.getEmail()).isNull();
				assertThat(customer.getDocumentNumber()).isNull();
			}catch (Exception e) {
				assertThat(e.getMessage()).isEqualTo("Invalid document number");
			}
		}
	}

	@Test
	void givenInvalidEmail_thenRefusesToRegisterCustomer() throws Exception {
		try { 
			String customerName = "Roberto Machado";
			String customerEmail = "roberto.machadobestmail.com";
			String documentNumber = "90779778057";

			CPF customerCPF = new CPF(documentNumber);
			Customer customer = new Customer(null, customerCPF, customerName, customerEmail);

			assertThat(customer.getName()).isEqualTo(customerName);
			assertThat(customer.getEmail()).isEqualTo(customerEmail);
			assertThat(customer.getDocumentNumber().getDocumentNumber()).isEqualTo(customerCPF.getDocumentNumber());
			assertThat(customer.getName()).isNull();
			assertThat(customer.getEmail()).isNull();
			assertThat(customer.getDocumentNumber()).isNull();
		}catch (Exception e) {
			assertThat(e.getMessage()).contains("invalid e-mail format");
		}
	}

	@Test
	void givenNullEmail_thenRefusesToRegisterCustomer() throws Exception {
		try { 
			String customerName = "Roberto Machado";
			String documentNumber = "90779778057";

			CPF customerCPF = new CPF(documentNumber);
			new Customer(null, customerCPF, customerName, null);
		}catch (Exception e) {
			assertThat(e.getMessage()).contains("Error validating customer data");
		}
	}

	@Test
	void givenEmptyEmail_thenRefusesToRegisterCustomer() throws Exception {
		try { 
			String customerName = "Roberto Machado";
			String documentNumber = "90779778057";
			String customerEmail = "";

			CPF customerCPF = new CPF(documentNumber);
			new Customer(null, customerCPF, customerName, customerEmail);
		}catch (Exception e) {
			assertThat(e.getMessage()).contains("Error validating customer data");
		}
	}

	@Test
	void givenBlankEmail_thenRefusesToRegisterCustomer() throws Exception {
		try { 
			String customerName = "Roberto Machado";
			String documentNumber = "90779778057";
			String customerEmail = " ";

			CPF customerCPF = new CPF(documentNumber);
			new Customer(null, customerCPF, customerName, customerEmail);
		}catch (Exception e) {
			assertThat(e.getMessage()).contains("Error validating customer data");
		}
	}

	@Test
	void givenEmptyName_thenRefusesToRegisterCustomer() throws Exception {
		try { 
			String customerName = "";
			String customerEmail = "roberto.machadobestmail.com";
			String documentNumber = "90779778057";

			CPF customerCPF = new CPF(documentNumber);
			Customer customer = new Customer(null, customerCPF, customerName, customerEmail);

			assertThat(customer.getName()).isEqualTo(customerName);
			assertThat(customer.getEmail()).isEqualTo(customerEmail);
			assertThat(customer.getDocumentNumber().getDocumentNumber()).isEqualTo(customerCPF.getDocumentNumber());
			assertThat(customer.getName()).isNull();
			assertThat(customer.getEmail()).isNull();
			assertThat(customer.getDocumentNumber()).isNull();
		}catch (Exception e) {
			assertThat(e.getMessage()).contains("empty, null or blank name");
		}
	}

	@Test
	void givenBlankName_thenRefusesToRegisterCustomer() throws Exception {
		try { 
			String customerName = " ";
			String customerEmail = "roberto.machadobestmail.com";
			String documentNumber = "90779778057";

			CPF customerCPF = new CPF(documentNumber);
			Customer customer = new Customer(null, customerCPF, customerName, customerEmail);

			assertThat(customer.getName()).isEqualTo(customerName);
			assertThat(customer.getEmail()).isEqualTo(customerEmail);
			assertThat(customer.getDocumentNumber().getDocumentNumber()).isEqualTo(customerCPF.getDocumentNumber());
			assertThat(customer.getName()).isNull();
			assertThat(customer.getEmail()).isNull();
			assertThat(customer.getDocumentNumber()).isNull();
		}catch (Exception e) {
			assertThat(e.getMessage()).contains("empty, null or blank name");
		}
	}

	@Test
	void givenNullName_thenRefusesToRegisterCustomer() throws Exception {
		try { 
			String customerName = null;
			String customerEmail = "roberto.machadobestmail.com";
			String documentNumber = "90779778057";

			CPF customerCPF = new CPF(documentNumber);
			Customer customer = new Customer(null, customerCPF, customerName, customerEmail);

			assertThat(customer.getName()).isEqualTo(customerName);
			assertThat(customer.getEmail()).isEqualTo(customerEmail);
			assertThat(customer.getDocumentNumber().getDocumentNumber()).isEqualTo(customerCPF.getDocumentNumber());
			assertThat(customer.getName()).isNull();
			assertThat(customer.getEmail()).isNull();
			assertThat(customer.getDocumentNumber()).isNull();
		}catch (Exception e) {
			assertThat(e.getMessage()).contains("empty, null or blank name");
		}
	}

	@Test
	void givenNewData_updateCustomer() throws Exception {
		String customerName = "Roberto Machado";
		String customerEmail = "roberto.machado@bestmail.com";
		String documentNumber = "90779778057";

		CPF customerCPF = new CPF(documentNumber);
		Customer customer = new Customer(null, customerCPF, customerName, customerEmail);

		customer.setDocumentNumber(new CPF("23640699041"));
		customer.setName("Roberto Facao");
		customer.setEmail("robertofacao@bestmail.com");
		customer.setId(1L);

		assertThat(customer.getName()).isEqualTo("Roberto Facao");
		assertThat(customer.getEmail()).isEqualTo("robertofacao@bestmail.com");
		assertThat(customer.getDocumentNumber().getDocumentNumber()).isEqualTo("23640699041");
	}
}
