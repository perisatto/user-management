package com.perisatto.fiapprj.menuguru.adapter.out;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.perisatto.fiapprj.menuguru.application.domain.model.CPF;
import com.perisatto.fiapprj.menuguru.application.port.out.LoadCustomerPort;

@Component
public class CustomerPersistenceApdapter implements LoadCustomerPort {
	
	private CustomerRepository customerRepository;
	
	public CustomerPersistenceApdapter(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}
	
	@Override
	public Optional<CustomerJpaEntity> loadCustomer(Long customerId) {
		return customerRepository.findById(customerId);
	}

	public Optional<CustomerJpaEntity> getCustomerByCPF(CPF customerDocument) {
		return customerRepository.findByDocumentNumber(customerDocument.getDocumentNumber());
	}
}
