package com.perisatto.fiapprj.menuguru.adapter.out;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.perisatto.fiapprj.menuguru.application.domain.model.CPF;
import com.perisatto.fiapprj.menuguru.application.domain.model.Customer;
import com.perisatto.fiapprj.menuguru.application.port.out.ManageCustomerPort;

@Component
public class CustomerPersistenceApdapter implements ManageCustomerPort {

	private CustomerRepository customerRepository;

	public CustomerPersistenceApdapter(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
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
	
	public Optional<Customer> getCustomerById(Long customerId) throws Exception{
		Customer customer;

		Optional<CustomerJpaEntity> customerJpaEntity = customerRepository.findById(customerId);
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

	@Override
	public Optional<Customer> updateCustomer(Customer customer) throws Exception {
		CustomerMapper customerMapper = new CustomerMapper();
		CustomerJpaEntity customerJpaEntity = customerMapper.mapToJpaEntity(customer);
		customerJpaEntity = customerRepository.save(customerJpaEntity);
		Customer updatedCustomer = customerMapper.mapToDomainEntity(customerJpaEntity);
		return Optional.of(updatedCustomer);
	}

	@Override
	public Boolean deleteCustomer(Long customerId) throws Exception {
		customerRepository.deleteById(customerId);
		return null;
	}
}
