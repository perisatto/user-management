package com.perisatto.fiapprj.menuguru_customer.infra.gateways;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.ActiveProfiles;

import com.perisatto.fiapprj.menuguru_customer.domain.entities.customer.CPF;
import com.perisatto.fiapprj.menuguru_customer.domain.entities.customer.Customer;
import com.perisatto.fiapprj.menuguru_customer.infra.gateways.mappers.CustomerMapper;
import com.perisatto.fiapprj.menuguru_customer.infra.persistences.entities.CustomerEntity;
import com.perisatto.fiapprj.menuguru_customer.infra.persistences.repositories.CustomerPersistenceRepository;

@ActiveProfiles(value = "test")
public class CustomerRepositoryJpaTest {

	private CustomerRepositoyJpa customerRepositoyJpa;
	
	@Mock
	private CustomerPersistenceRepository customerRepository;
	
	private CustomerMapper customerMapper;
	
	AutoCloseable openMocks;
	
	@BeforeEach
	void setUp() {
		openMocks = MockitoAnnotations.openMocks(this);
		customerMapper = new CustomerMapper();
		customerRepositoyJpa = new CustomerRepositoyJpa(customerRepository, customerMapper);
	}
	
	@AfterEach
	void tearDown() throws Exception {
		openMocks.close();
	}
	
	
	@Test
	void givenValidData_thenSavesCustomer() throws Exception {
		
		CustomerEntity customerEntity = new CustomerEntity();
		
		customerEntity.setDocumentNumber("67505036084");
		customerEntity.seteMail("robertomachado@bestmail.com");
		customerEntity.setIdCustomer(10L);
		customerEntity.setIdCustomerStatus(1L);
		customerEntity.setName("Roberto Machado");
		
		when(customerRepository.save(any()))
		.thenReturn(customerEntity);
		
		String name = "Roberto Machado";
		String email = "robertomachado@bestmail.com";
		String documentNumber = "67505036084";
		
		CPF cpf = new CPF(documentNumber);
		
		Customer customer = new Customer(null, cpf, name, email);
		
		customerRepositoyJpa.createCustomer(customer);
		
		verify(customerRepository, times(1)).save(any());
	}
	
	@Test
	void givenValidCPF_thenRetrievesCustomer() throws Exception {
		CustomerEntity customerEntity = new CustomerEntity();
		
		customerEntity.setDocumentNumber("67505036084");
		customerEntity.seteMail("robertomachado@bestmail.com");
		customerEntity.setIdCustomer(10L);
		customerEntity.setIdCustomerStatus(1L);
		customerEntity.setName("Roberto Machado");
		
		when(customerRepository.findByDocumentNumberAndIdCustomerStatus(any(String.class), any(Long.class)))
		.thenReturn(Optional.of(customerEntity));
		
		String documentNumber = "67505036084";
		
		CPF cpf = new CPF(documentNumber);
		
		customerRepositoyJpa.getCustomerByCPF(cpf);
		
		verify(customerRepository, times(1)).findByDocumentNumberAndIdCustomerStatus(any(String.class), any(Long.class));
	}
	
	@Test
	void givenInexistentCPF_thenRefusesRetrieveCustomer() throws Exception {
	
		when(customerRepository.findByDocumentNumberAndIdCustomerStatus(any(String.class), any(Long.class)))
		.thenReturn(Optional.empty());
		
		String documentNumber = "67505036084";
		
		CPF cpf = new CPF(documentNumber);
		
		customerRepositoyJpa.getCustomerByCPF(cpf);
		
		verify(customerRepository, times(1)).findByDocumentNumberAndIdCustomerStatus(any(String.class), any(Long.class));
	}
	
	@Test
	void givenValidId_thenRetrieveCustomer() throws Exception {
	
		CustomerEntity customerEntity = new CustomerEntity();
		
		customerEntity.setDocumentNumber("67505036084");
		customerEntity.seteMail("robertomachado@bestmail.com");
		customerEntity.setIdCustomer(10L);
		customerEntity.setIdCustomerStatus(1L);
		customerEntity.setName("Roberto Machado");
		
		when(customerRepository.findByIdCustomerAndIdCustomerStatus(any(Long.class), any(Long.class)))
		.thenReturn(Optional.of(customerEntity));
		
		Long customerId = 10L;
		
		customerRepositoyJpa.getCustomerById(customerId);
		
		verify(customerRepository, times(1)).findByIdCustomerAndIdCustomerStatus(any(Long.class), any(Long.class));
	}
	
	@Test
	void givenInexistentId_thenRefusesRetrieveCustomer() throws Exception {
	
		when(customerRepository.findByIdCustomerAndIdCustomerStatus(any(Long.class), any(Long.class)))
		.thenReturn(Optional.empty());
		
		Long customerId = 10L;
		
		customerRepositoyJpa.getCustomerById(customerId);
		
		verify(customerRepository, times(1)).findByIdCustomerAndIdCustomerStatus(any(Long.class), any(Long.class));
	}
	
	@Test
	void listAllCustomers() throws Exception {
		when(customerRepository.findByIdCustomerStatus(any(Long.class), any()))
		.thenAnswer(i -> {
			
			List<CustomerEntity> customerList = new ArrayList<>();
			
			CustomerEntity customerEntity = new CustomerEntity();
			
			customerEntity.setDocumentNumber("67505036084");
			customerEntity.seteMail("robertomachado@bestmail.com");
			customerEntity.setIdCustomer(10L);
			customerEntity.setIdCustomerStatus(1L);
			customerEntity.setName("Roberto Machado");
			
			customerList.add(customerEntity);
			
			CustomerEntity customerEntity2 = new CustomerEntity();
			
			customerEntity2.setDocumentNumber("67505036084");
			customerEntity2.seteMail("robertomachado@bestmail.com");
			customerEntity2.setIdCustomer(20L);
			customerEntity2.setIdCustomerStatus(1L);
			customerEntity2.setName("Roberto Machado");
			
			customerList.add(customerEntity2);
			
			Page<CustomerEntity> customers = new PageImpl<>(customerList);
			
			return customers;
		});
		
		customerRepositoyJpa.findAll(50, 1);
		
		verify(customerRepository, times(1)).findByIdCustomerStatus(any(Long.class), any());
	}
	
	@Test
	void givenValidData_UpdatesCustomer() throws Exception {
		CustomerEntity customerEntity = new CustomerEntity();
		
		customerEntity.setDocumentNumber("67505036084");
		customerEntity.seteMail("robertomachado@bestmail.com");
		customerEntity.setIdCustomer(10L);
		customerEntity.setIdCustomerStatus(1L);
		customerEntity.setName("Roberto Machado");
		
		when(customerRepository.save(any(CustomerEntity.class)))
		.thenReturn(customerEntity);
		
		String name = "Roberto Machado";
		String email = "robertomachado@bestmail.com";
		String documentNumber = "67505036084";
		
		CPF cpf = new CPF(documentNumber);
		
		Customer customer = new Customer(null, cpf, name, email);
		
		customerRepositoyJpa.updateCustomer(customer);
		
		verify(customerRepository, times(1)).save(any(CustomerEntity.class));
	}
	
	@Test
	void givenValidId_thenDeletesCustomer() throws Exception {
		CustomerEntity customerEntity = new CustomerEntity();
		
		customerEntity.setDocumentNumber("67505036084");
		customerEntity.seteMail("robertomachado@bestmail.com");
		customerEntity.setIdCustomer(10L);
		customerEntity.setIdCustomerStatus(1L);
		customerEntity.setName("Roberto Machado");
		
		when(customerRepository.findById(any(Long.class)))
		.thenReturn(Optional.of(customerEntity));
		
		Boolean deleted = customerRepositoyJpa.deleteCustomer(10L);
		
		assertThat(deleted).isTrue();
	}
	
	@Test
	void givenInvalidId_thenDeletesCustomer() throws Exception {		
		when(customerRepository.findById(any(Long.class)))
		.thenReturn(Optional.empty());
		
		Boolean deleted = customerRepositoyJpa.deleteCustomer(10L);
		
		assertThat(deleted).isFalse();
	}
}
