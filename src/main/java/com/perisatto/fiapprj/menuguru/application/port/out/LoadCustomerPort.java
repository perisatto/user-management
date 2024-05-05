package com.perisatto.fiapprj.menuguru.application.port.out;

import java.util.Optional;

import com.perisatto.fiapprj.menuguru.application.domain.model.CPF;
import com.perisatto.fiapprj.menuguru.application.domain.model.Customer;


public interface LoadCustomerPort {
	Optional<Customer> getCustomerByCPF(CPF customerDocument) throws Exception;
	
	Optional<Customer> getCustomerById(Long customerId) throws Exception;
}
