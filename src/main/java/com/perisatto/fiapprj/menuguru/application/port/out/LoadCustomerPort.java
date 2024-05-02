package com.perisatto.fiapprj.menuguru.application.port.out;

import java.util.Optional;

import com.perisatto.fiapprj.menuguru.adapter.out.CustomerJpaEntity;
import com.perisatto.fiapprj.menuguru.application.domain.model.CPF;


public interface LoadCustomerPort {
	Optional<CustomerJpaEntity> loadCustomer(Long customerId);
	
	Optional<CustomerJpaEntity> getCustomerByCPF(CPF customerDocument);
}
