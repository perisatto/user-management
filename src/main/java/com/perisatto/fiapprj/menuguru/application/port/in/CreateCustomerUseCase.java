package com.perisatto.fiapprj.menuguru.application.port.in;

import com.perisatto.fiapprj.menuguru.application.domain.model.Customer;

public interface CreateCustomerUseCase {
	
	Customer createCustomer(String documentNumber, String name, String email);

	Customer getCustomerByCPF(String documentNumber);
}
