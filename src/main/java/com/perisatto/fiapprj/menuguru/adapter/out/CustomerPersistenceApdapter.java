package com.perisatto.fiapprj.menuguru.adapter.out;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.perisatto.fiapprj.menuguru.application.domain.model.CPF;
import com.perisatto.fiapprj.menuguru.application.domain.model.Customer;
import com.perisatto.fiapprj.menuguru.application.port.out.CreateCustomerPort;
import com.perisatto.fiapprj.menuguru.application.port.out.LoadCustomerPort;

@Component
public class CustomerPersistenceApdapter implements LoadCustomerPort, CreateCustomerPort {

	private CustomerRepository customerRepository;

	public CustomerPersistenceApdapter(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}

	@Override
	public Optional<CustomerJpaEntity> loadCustomer(Long customerId) throws Exception {
		return customerRepository.findById(customerId);
	}

	public Optional<Customer> getCustomerByCPF(CPF customerDocument) throws Exception {
		Customer customer;

		Optional<CustomerJpaEntity> customerJpaEntity = customerRepository.findByDocumentNumber(customerDocument.getDocumentNumber());
		if(customerJpaEntity.isPresent()) {
			CustomerMapper customerMapper = new CustomerMapper();				
			customer = customerMapper.mapToDomainEntity(customerJpaEntity.get());
		}else {
			return Optional.empty();
		}

		return Optional.of(customer);
	}

	@Override
	public Customer createCustomer(Customer customer) throws Exception {
		CustomerMapper customerMapper = new CustomerMapper();
		CustomerJpaEntity customerJpaEntity =  customerMapper.mapToJpaEntity(customer);
		customerJpaEntity = customerRepository.save(customerJpaEntity);
		Customer newCustomer;
		newCustomer = customerMapper.mapToDomainEntity(customerJpaEntity);
		return newCustomer;
	}
}
