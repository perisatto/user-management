package com.perisatto.fiapprj.user_management.infra.controllers;

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
import com.perisatto.fiapprj.user_management.application.usecases.UserManagementUseCase;
import com.perisatto.fiapprj.user_management.domain.entities.user.CPF;
import com.perisatto.fiapprj.user_management.domain.entities.user.User;
import com.perisatto.fiapprj.user_management.infra.controllers.UserManagementRestController;
import com.perisatto.fiapprj.user_management.infra.controllers.dtos.CreateUserRequestDTO;
import com.perisatto.fiapprj.user_management.infra.controllers.dtos.UpdateUserRequestDTO;

@ActiveProfiles(value = "test")
public class UserManagementRestControllerTest {

	private MockMvc mockMvc;

	@Mock
	private UserManagementUseCase userUseCase;

	@Mock
	private Properties requestProperties;

	AutoCloseable openMocks;

	@BeforeEach
	void setUp() {
		openMocks = MockitoAnnotations.openMocks(this);
		UserManagementRestController userRestController = new UserManagementRestController(userUseCase, requestProperties);

		mockMvc = MockMvcBuilders.standaloneSetup(userRestController)				
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
		void givenValidData_registerUser() throws Exception {

			User userData = new User(10L, new CPF("35732996010"), "Roberto Machado", "roberto.machado@bestmail.com");

			when(userUseCase.createUser(any(String.class), any(String.class), any(String.class)))
			.thenReturn(userData);

			CreateUserRequestDTO requestMessage = new CreateUserRequestDTO();
			requestMessage.setName("Roberto Machado");
			requestMessage.setEmail("robertomachado@bestmail.com");
			requestMessage.setDocumentNumber("67505036084");

			mockMvc.perform(post("/users")
					.contentType(MediaType.APPLICATION_JSON)
					.content(asJsonString(requestMessage)))
			.andExpect(status().isCreated());

			verify(userUseCase, times(1)).createUser(any(String.class), any(String.class), any(String.class));

		}				
	}

	@Nested
	class ConsultarCliente {
		
		@Test
		void givenValidCPF_retrievesUserData() throws Exception {
			User userData = new User(10L, new CPF("35732996010"), "Roberto Machado", "roberto.machado@bestmail.com");
			
			when(userUseCase.getUserByCPF(any(String.class)))
			.thenReturn(userData);
			
			String documentNumber = "35732996010"; 
			
			mockMvc.perform(get("/usersByCPF/{documentNumber}", documentNumber)
					.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());
			
			verify(userUseCase, times(1)).getUserByCPF(any(String.class));
		}
		
		@Test
		void givenValidId_retrievesUserData() throws Exception {
			User userData = new User(10L, new CPF("35732996010"), "Roberto Machado", "roberto.machado@bestmail.com");
			
			when(userUseCase.getUserById(any(Long.class)))
			.thenReturn(userData);
			
			Long userId = 10L; 
			
			mockMvc.perform(get("/users/{userId}", userId)
					.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());
			
			verify(userUseCase, times(1)).getUserById(any(Long.class));
		}
		
		@Test
		void listUserData() throws Exception {
		
			when(userUseCase.findAllUsers(any(Integer.class), any(Integer.class)))
			.thenAnswer(i -> {
				Set<User> result = new LinkedHashSet<User>();
				User userData1 = new User(10L, new CPF("65678860054"), "Roberto Machado", "roberto.machado@bestmail.com");
				User userData2 = new User(20L, new CPF("65678860054"), "Roberto Machado", "roberto.machado@bestmail.com");
				result.add(userData1);
				result.add(userData2);
				return result;
			});
			
			mockMvc.perform(get("/users")
					.contentType(MediaType.APPLICATION_JSON)
					.param("_page", "1")
					.param("_size", "50"))
			.andExpect(status().isOk());
			
			verify(userUseCase, times(1)).findAllUsers(any(Integer.class), any(Integer.class));
		}
	}
	
	@Nested
	class AtualizarCliente {
		
		@Test
		void givenNewData_updateUser() throws Exception {
			
			User userData = new User(10L, new CPF("35732996010"), "Roberto Machado", "roberto.machado@bestmail.com");
			
			when(userUseCase.updateUser(any(Long.class), any(String.class), any(String.class), any(String.class)))
			.thenReturn(userData);
			
			UpdateUserRequestDTO userNewData = new UpdateUserRequestDTO();
			userNewData.setName("Roberto Machado");
			userNewData.setEmail("robertomachado@bestmail.com");
			userNewData.setDocumentNumber("67505036084");
			
			Long userId = 10L;
			
			mockMvc.perform(patch("/users/{userId}", userId)
					.contentType(MediaType.APPLICATION_JSON)
					.content(asJsonString(userNewData)))
			.andExpect(status().isOk());
			
			verify(userUseCase, times(1)).updateUser(any(Long.class), any(String.class), any(String.class), any(String.class));
		}
	}
	
	@Nested
	class ApagarCliente {
		
		@Test
		void givenValidId_thenDeletesUser() throws Exception {
			
			when(userUseCase.deleteUser(any(Long.class)))
			.thenReturn(true);
			
			Long userId = 10L;
			
			mockMvc.perform(delete("/users/{userId}", userId)
					.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isNoContent());
			
			verify(userUseCase, times(1)).deleteUser(any(Long.class));
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
