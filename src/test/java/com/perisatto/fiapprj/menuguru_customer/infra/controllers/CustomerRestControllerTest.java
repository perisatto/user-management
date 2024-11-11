package com.perisatto.fiapprj.menuguru_customer.infra.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.LinkedHashSet;
import java.util.Properties;
import java.util.Set;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.perisatto.fiapprj.menuguru_customer.application.usecases.CustomerUseCase;
import com.perisatto.fiapprj.menuguru_customer.domain.entities.customer.CPF;
import com.perisatto.fiapprj.menuguru_customer.domain.entities.customer.Customer;
import com.perisatto.fiapprj.menuguru_customer.infra.controllers.dtos.CreateCustomerRequestDTO;
import com.perisatto.fiapprj.menuguru_customer.infra.controllers.dtos.UpdateCustomerRequestDTO;

@ActiveProfiles(value = "test")
public class CustomerRestControllerTest {

	private MockMvc mockMvc;

	@Mock
	private CustomerUseCase customerUseCase;

	@Mock
	private Properties requestProperties;

	AutoCloseable openMocks;

	@BeforeEach
	void setUp() {
		openMocks = MockitoAnnotations.openMocks(this);
		CustomerRestController customerRestController = new CustomerRestController(customerUseCase, requestProperties);

		mockMvc = MockMvcBuilders.standaloneSetup(customerRestController)				
				.addFilter((request, response, chain) -> {
					response.setCharacterEncoding("UTF-8");
					chain.doFilter(request, response);					
				}, "/*")
				.build();
	}

	@AfterEach
	void tearDown() throws Exception {
		openMocks.close();
	}

	@Nested
	class RegistrarCliente {

		@Test
		void givenValidData_registerCustomer() throws Exception {

			Customer customerData = new Customer(10L, new CPF("35732996010"), "Roberto Machado", "roberto.machado@bestmail.com");

			when(customerUseCase.createCustomer(any(String.class), any(String.class), any(String.class)))
			.thenReturn(customerData);

			CreateCustomerRequestDTO requestMessage = new CreateCustomerRequestDTO();
			requestMessage.setName("Roberto Machado");
			requestMessage.setEmail("robertomachado@bestmail.com");
			requestMessage.setDocumentNumber("67505036084");

			mockMvc.perform(post("/customers")
					.contentType(MediaType.APPLICATION_JSON)
					.content(asJsonString(requestMessage)))
			.andExpect(status().isCreated());

			verify(customerUseCase, times(1)).createCustomer(any(String.class), any(String.class), any(String.class));

		}				
	}

	@Nested
	class ConsultarCliente {
		
		@Test
		void givenValidCPF_retrievesCustomerData() throws Exception {
			Customer customerData = new Customer(10L, new CPF("35732996010"), "Roberto Machado", "roberto.machado@bestmail.com");
			
			when(customerUseCase.getCustomerByCPF(any(String.class)))
			.thenReturn(customerData);
			
			String documentNumber = "35732996010"; 
			
			mockMvc.perform(get("/customersByCPF/{documentNumber}", documentNumber)
					.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());
			
			verify(customerUseCase, times(1)).getCustomerByCPF(any(String.class));
		}
		
		@Test
		void givenValidId_retrievesCustomerData() throws Exception {
			Customer customerData = new Customer(10L, new CPF("35732996010"), "Roberto Machado", "roberto.machado@bestmail.com");
			
			when(customerUseCase.getCustomerById(any(Long.class)))
			.thenReturn(customerData);
			
			Long customerId = 10L; 
			
			mockMvc.perform(get("/customers/{customerId}", customerId)
					.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());
			
			verify(customerUseCase, times(1)).getCustomerById(any(Long.class));
		}
		
		@Test
		void listCustomerData() throws Exception {
		
			when(customerUseCase.findAllCustomers(any(Integer.class), any(Integer.class)))
			.thenAnswer(i -> {
				Set<Customer> result = new LinkedHashSet<Customer>();
				Customer customerData1 = new Customer(10L, new CPF("65678860054"), "Roberto Machado", "roberto.machado@bestmail.com");
				Customer customerData2 = new Customer(20L, new CPF("65678860054"), "Roberto Machado", "roberto.machado@bestmail.com");
				result.add(customerData1);
				result.add(customerData2);
				return result;
			});
			
			mockMvc.perform(get("/customers")
					.contentType(MediaType.APPLICATION_JSON)
					.param("_page", "1")
					.param("_size", "50"))
			.andExpect(status().isOk());
			
			verify(customerUseCase, times(1)).findAllCustomers(any(Integer.class), any(Integer.class));
		}
	}
	
	@Nested
	class AtualizarCliente {
		
		@Test
		void givenNewData_updateCustomer() throws Exception {
			
			Customer customerData = new Customer(10L, new CPF("35732996010"), "Roberto Machado", "roberto.machado@bestmail.com");
			
			when(customerUseCase.updateCustomer(any(Long.class), any(String.class), any(String.class), any(String.class)))
			.thenReturn(customerData);
			
			UpdateCustomerRequestDTO customerNewData = new UpdateCustomerRequestDTO();
			customerNewData.setName("Roberto Machado");
			customerNewData.setEmail("robertomachado@bestmail.com");
			customerNewData.setDocumentNumber("67505036084");
			
			Long customerId = 10L;
			
			mockMvc.perform(patch("/customers/{customerId}", customerId)
					.contentType(MediaType.APPLICATION_JSON)
					.content(asJsonString(customerNewData)))
			.andExpect(status().isOk());
			
			verify(customerUseCase, times(1)).updateCustomer(any(Long.class), any(String.class), any(String.class), any(String.class));
		}
	}
	
	@Nested
	class ApagarCliente {
		
		@Test
		void givenValidId_thenDeletesCustomer() throws Exception {
			
			when(customerUseCase.deleteCustomer(any(Long.class)))
			.thenReturn(true);
			
			Long customerId = 10L;
			
			mockMvc.perform(delete("/customers/{customerId}", customerId)
					.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isNoContent());
			
			verify(customerUseCase, times(1)).deleteCustomer(any(Long.class));
		}
	}
	
	public static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
