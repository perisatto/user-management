package com.perisatto.fiapprj.menuguru_customer.infra.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;
import java.util.Properties;

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
	
	  public static String asJsonString(final Object obj) {
		    try {
		      return new ObjectMapper().writeValueAsString(obj);
		    } catch (Exception e) {
		      throw new RuntimeException(e);
		    }
		  }
}
