package com.perisatto.fiapprj.menuguru.application.port.in;

import com.perisatto.fiapprj.menuguru.application.domain.model.Customer;

public interface ManageCustomerUseCase {
	
	Customer createCustomer(String documentNumber, String name, String email) throws Exception;

	Customer getCustomerByCPF(String documentNumber) throws Exception;
	
	Customer getCustomerById(Long customerId) throws Exception;
	
	Customer updateCustomer(Long customerId, String documentNumber, String customerName, String customerEmail) throws Exception;
}
