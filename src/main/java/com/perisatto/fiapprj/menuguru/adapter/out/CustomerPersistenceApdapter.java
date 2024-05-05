package com.perisatto.fiapprj.menuguru.adapter.out;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
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

	@Override
	public Set<Customer> findAll(Integer limit, Integer page) throws Exception {
		Pageable pageable = PageRequest.of(page, limit, Sort.by("idCustomer"));
		Page<CustomerJpaEntity> customers = customerRepository.findAll(pageable);
		Set<Customer> customersSet = new LinkedHashSet<Customer>();
		
		for (CustomerJpaEntity customer : customers) {
			Customer retrievedCustomer = new Customer(customer.getIdCustomer(), new CPF(customer.getDocumentNumber()), customer.getName(), customer.geteMail());
			customersSet.add(retrievedCustomer);
		}
		return customersSet;
	}
}
