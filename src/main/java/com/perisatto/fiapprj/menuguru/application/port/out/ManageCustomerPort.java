package com.perisatto.fiapprj.menuguru.application.port.out;

import java.util.Optional;

import com.perisatto.fiapprj.menuguru.application.domain.model.CPF;
import com.perisatto.fiapprj.menuguru.application.domain.model.Customer;

public interface ManageCustomerPort {
	Customer createCustomer(Customer customer) throws Exception;
	
	Optional<Customer> getCustomerByCPF(CPF customerDocument) throws Exception;
	
	Optional<Customer> getCustomerById(Long customerId) throws Exception;
	
	Optional<Customer> updateCustomer(Customer customer) throws Exception;
}
