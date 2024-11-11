package com.perisatto.fiapprj.menuguru_customer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.perisatto.fiapprj.menuguru_customer.application.interfaces.CustomerRepository;
import com.perisatto.fiapprj.menuguru_customer.application.interfaces.UserManagement;
import com.perisatto.fiapprj.menuguru_customer.application.usecases.CustomerUseCase;
import com.perisatto.fiapprj.menuguru_customer.infra.gateways.CustomerRepositoyJpa;
import com.perisatto.fiapprj.menuguru_customer.infra.gateways.mappers.CustomerMapper;
import com.perisatto.fiapprj.menuguru_customer.infra.persistences.repositories.CustomerPersistenceRepository;

@Configuration
public class CustomerConfig {

	@Bean
	CustomerUseCase customerUseCase(CustomerRepository customerRepository, UserManagement userManagement) {
		return new CustomerUseCase(customerRepository, userManagement);
	}	
	
	@Bean
	CustomerRepositoyJpa customerJpaRepository(CustomerPersistenceRepository customerPersistenceRepository, CustomerMapper customerMapper) {
		return new CustomerRepositoyJpa(customerPersistenceRepository, customerMapper);
	}
	
	@Bean
	CustomerMapper customerMapper() {
		return new CustomerMapper();
	}
}
