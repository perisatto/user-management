package com.perisatto.fiapprj.menuguru.application.port.out;

import com.perisatto.fiapprj.menuguru.application.domain.model.Customer;

public interface CreateCustomerPort {
	Integer createCustomer(Customer customer);
}
