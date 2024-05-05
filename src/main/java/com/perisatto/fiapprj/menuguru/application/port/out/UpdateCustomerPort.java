package com.perisatto.fiapprj.menuguru.application.port.out;

import java.util.Optional;

import com.perisatto.fiapprj.menuguru.application.domain.model.Customer;

public interface UpdateCustomerPort {
	Optional<Customer> updateCustomer(Customer customer) throws Exception;
}
