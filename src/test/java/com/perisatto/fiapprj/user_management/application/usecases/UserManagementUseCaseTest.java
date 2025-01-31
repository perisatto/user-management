package com.perisatto.fiapprj.user_management.application.usecases;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ActiveProfiles;

import com.perisatto.fiapprj.user_management.application.interfaces.UserManagementRepository;
import com.perisatto.fiapprj.user_management.application.interfaces.IAMUserManagement;
import com.perisatto.fiapprj.user_management.application.usecases.UserManagementUseCase;
import com.perisatto.fiapprj.user_management.domain.entities.user.CPF;
import com.perisatto.fiapprj.user_management.domain.entities.user.IAMUser;
import com.perisatto.fiapprj.user_management.domain.entities.user.User;
import com.perisatto.fiapprj.user_management.handler.exceptions.NotFoundException;
import com.perisatto.fiapprj.user_management.handler.exceptions.ValidationException;

@ActiveProfiles(value = "test")
public class UserManagementUseCaseTest {

	private UserManagementUseCase userUseCase;

	@Mock
	private IAMUserManagement userManagement;

	@Mock
	private UserManagementRepository userRepository;

	AutoCloseable openMocks;

	@BeforeEach
	void setUp() {
		openMocks = MockitoAnnotations.openMocks(this);
		userUseCase = new UserManagementUseCase(userRepository, userManagement);
	}

	@AfterEach
	void tearDown() throws Exception {
		openMocks.close();
	}


	@Nested
	class RegistrarCliente {

		@Test
		void givenValidCPF_thenRegisteruser() throws Exception {		
			when(userRepository.createUser(any(User.class)))
			.thenAnswer(i -> i.getArgument(0));

			when(userManagement.createUser(any(IAMUser.class)))
			.thenAnswer(u -> u.getArgument(0));

			String userName = "Roberto Machado";
			String userEmail = "roberto.machado@bestmail.com";
			String documentNumber = "35732996010";

			User user = userUseCase.createUser(documentNumber, userName, userEmail);

			assertThat(user.getDocumentNumber().getDocumentNumber()).isEqualTo(documentNumber);
			assertThat(user.getName()).isEqualTo(userName);
			assertThat(user.getEmail()).isEqualTo(userEmail);
		}

		@Test
		void givenAlreadyExistentuser_thenRefusesToCreateuser() throws Exception {
			User userData = new User(10L, new CPF("35732996010"), "Roberto Machado", "roberto.machado@bestmail.com");

			when(userRepository.getUserByCPF(any(CPF.class)))
			.thenReturn(Optional.of(userData));

			String userName = "Roberto Machado";
			String userEmail = "roberto.machado@bestmail.com";
			String documentNumber = "35732996010";

			try {
				userUseCase.createUser(documentNumber, userName, userEmail);
			} catch (ValidationException e) {
				assertThat(e.getMessage()).isEqualTo("User already exists");
			} 	
		}

		@Test
		void givenInvalidCPF_thenRefusesToRegisteruser() throws Exception {

			when(userRepository.createUser(any(User.class)))
			.thenAnswer(i -> i.getArgument(0));

			when(userManagement.createUser(any(IAMUser.class)))
			.thenAnswer(u -> u.getArgument(0));

			String userName = "Roberto Machado";
			String userEmail = "roberto.machado@bestmail.com";
			String documentNumber = "90779778058";

			try {
				userUseCase.createUser(documentNumber, userName, userEmail);
			} catch (ValidationException e) {
				assertThat(e.getMessage()).isEqualTo("Invalid document number");
			} catch (Exception e) {
				assertThat(e.getMessage()).isNotEqualTo("Invalid document number");
			}
		}

		@Test
		void givenInvalidEmail_thenRefusesToRegisteruser() throws Exception {

			when(userRepository.createUser(any(User.class)))
			.thenAnswer(i -> i.getArgument(0));

			when(userManagement.createUser(any(IAMUser.class)))
			.thenAnswer(u -> u.getArgument(0));

			String userName = "Roberto Machado";
			String userEmail = "roberto.machadobestmail.com";
			String documentNumber = "90779778057";

			try {
				userUseCase.createUser(documentNumber, userName, userEmail);
			} catch (ValidationException e) {
				assertThat(e.getMessage()).contains("invalid e-mail format");
			} catch (Exception e) {
				assertThat(e.getMessage()).doesNotContain("invalid e-mail format");
			}
		}

		@Test
		void givenEmptyName_thenRefusesToRegisteruser() throws Exception {

			when(userRepository.createUser(any(User.class)))
			.thenAnswer(i -> i.getArgument(0));

			when(userManagement.createUser(any(IAMUser.class)))
			.thenAnswer(u -> u.getArgument(0));			

			String userName = "";
			String userEmail = "roberto.machadobestmail.com";
			String documentNumber = "90779778057";

			try {
				userUseCase.createUser(documentNumber, userName, userEmail);
			} catch (ValidationException e) {
				assertThat(e.getMessage()).contains("empty, null or blank name");
			} catch (Exception e) {
				assertThat(e.getMessage()).doesNotContain("empty, null or blank name");
			}
		}		
	}


	@Nested
	class ConsultarCliente {

		@Test
		void givenCPF_thenGetuserData() throws Exception {

			User userData = new User(10L, new CPF("90779778057"), "Roberto Machado", "roberto.machado@bestmail.com");

			when(userRepository.getUserByCPF(any(CPF.class)))
			.thenReturn(Optional.of(userData));
			
			String documentNumber = "90779778057";

			User user = userUseCase.getUserByCPF(documentNumber);

			assertThat(user.getDocumentNumber().getDocumentNumber()).isEqualTo(documentNumber);
		}

		@Test
		void givenInexistentCPF_thenGetNotFound () throws Exception {

			when(userRepository.getUserByCPF(any(CPF.class)))
			.thenReturn(Optional.empty());			

			try {
				String documentNumber = "35732996010";

				UserManagementUseCase newuserUseCase = new UserManagementUseCase(userRepository, userManagement);

				newuserUseCase.getUserByCPF(documentNumber);
			} catch (NotFoundException e) {
				assertThat(e.getMessage()).isEqualTo("User not found");
			}

		}

		@Test
		void givenValidId_thenGetuser () throws Exception {

			User userData = new User(10L, new CPF("90779778057"), "Roberto Machado", "roberto.machado@bestmail.com");			

			when(userRepository.getUserById(10L))
			.thenReturn(Optional.of(userData));	

			Long userId = 10L;

			try {
				User user = userUseCase.getUserById(userId);

				assertThat(user.getId()).isEqualTo(userId);
			} catch (ValidationException e) {
				assertThat(e.getMessage()).doesNotContain("user not found");
			}
		}

		@Test
		void giveInexistentId_thenGetuserNotFound () throws Exception {

			when(userRepository.getUserById(any(Long.class)))
			.thenReturn(Optional.empty());

			try {
				Long userId = 20L;

				User user = userUseCase.getUserById(userId);

				assertThat(user.getName()).isNullOrEmpty();
			} catch (NotFoundException e) {
				assertThat(e.getMessage()).isEqualTo("User not found");
			} 
		}
		
		@Test
		void listusers() throws Exception {
			when(userRepository.findAll(any(Integer.class), any(Integer.class)))
			.thenAnswer(i -> {
				Set<User> result = new LinkedHashSet<User>();
				User userData1 = new User(10L, new CPF("65678860054"), "Roberto Machado", "roberto.machado@bestmail.com");
				User userData2 = new User(20L, new CPF("65678860054"), "Roberto Machado", "roberto.machado@bestmail.com");
				result.add(userData1);
				result.add(userData2);
				return result;
			});
			
			Set<User> result = userUseCase.findAllUsers(null, null);
			
			assertThat(result.size()).isEqualTo(2);
		}
		
		@Test
		void givenInvalidParameters_RefusesListuser() throws Exception {
			try {
				userUseCase.findAllUsers(100, null);
			} catch (ValidationException e) {
				assertThat(e.getMessage()).contains("Invalid size parameter");
			}
			
			try {
				userUseCase.findAllUsers(-1, null);
			} catch (ValidationException e) {
				assertThat(e.getMessage()).contains("Invalid size parameter");
			}
			
			try {
				userUseCase.findAllUsers(null, 0);
			} catch (ValidationException e) {
				assertThat(e.getMessage()).contains("Invalid page parameter");
			}
		}
	}

	@Nested
	class AtualizarCliente {
		
		@Test
		void givenNewData_thenUpdateuser () throws Exception {		

			User userData = new User(10L, new CPF("65678860054"), "Roberto Machado", "roberto.machado@bestmail.com");

			when(userRepository.getUserById(any(Long.class)))
			.thenReturn(Optional.of(userData));

			when(userRepository.updateUser(any(User.class)))
			.thenAnswer(i -> 
			{ 
				Optional<User> user = Optional.of(i.getArgument(0));
				return user;
			});

			String userName = "Roberto Facao";
			String userEmail = "roberto.facao@bestmail.com";
			String documentNumber = "65678860054";

			User newuserData = userUseCase.updateUser(10L, documentNumber, userName, userEmail);

			assertThat(newuserData.getId()).isEqualTo(10L);
			assertThat(newuserData.getDocumentNumber().getDocumentNumber()).isEqualTo(documentNumber);
			assertThat(newuserData.getName()).isEqualTo(userName);
			assertThat(newuserData.getEmail()).isEqualTo(userEmail);
		}
		
		
		@Test
		void givenNewName_thenUpdateName () throws Exception {		

			User userData = new User(10L, new CPF("65678860054"), "Roberto Machado", "roberto.machado@bestmail.com");

			when(userRepository.getUserById(any(Long.class)))
			.thenReturn(Optional.of(userData));

			when(userRepository.updateUser(any(User.class)))
			.thenAnswer(i -> 
			{ 
				Optional<User> user = Optional.of(i.getArgument(0));
				return user;
			});

			String userName = "Roberto Facao";

			User newuserData = userUseCase.updateUser(10L, null, userName, null);

			assertThat(newuserData.getId()).isEqualTo(10L);
			assertThat(newuserData.getName()).isEqualTo(userName);
		}

		@Test
		void givenNewEmail_thenUpdateEmail () throws Exception {
			User userData = new User(10L, new CPF("65678860054"), "Roberto Machado", "roberto.machado@bestmail.com");

			when(userRepository.getUserById(any(Long.class)))
			.thenReturn(Optional.of(userData));

			when(userRepository.updateUser(any(User.class)))
			.thenAnswer(i -> 
			{ 
				Optional<User> user = Optional.of(i.getArgument(0));
				return user;
			});

			Long userId = 10L;
			String userEmail = "roberto.facao@bestmail.com"; 

			User newuserData = userUseCase.updateUser(userId, null, null, userEmail);


			assertThat(newuserData.getId()).isEqualTo(10L);
			assertThat(newuserData.getEmail()).isEqualTo(userEmail);
		}

		@Test
		void duringDatabaseProblem_RefusesUpdateuser() throws Exception {
			User userData = new User(10L, new CPF("65678860054"), "Roberto Machado", "roberto.machado@bestmail.com");

			when(userRepository.getUserById(any(Long.class)))
			.thenReturn(Optional.of(userData));
			
			when(userRepository.updateUser(any(User.class)))
			.thenReturn(Optional.empty());
			
			Long userId = 10L;
			String userName = "Roberto Facao";
			String userEmail = "roberto.facao@bestmail.com";
			String documentNumber = "65678860054";
			
			try {
				userUseCase.updateUser(userId, documentNumber, userName, userEmail);
			} catch (Exception e) {
				assertThatExceptionOfType(Exception.class);
				assertThat(e.getMessage()).contains("Error while updating user data. Please refer to application log for details.");
			}

		}
		
		@Test
		void givenInvalidId_RefusesUpdateuser() throws Exception {
			when(userRepository.getUserById(any(Long.class)))
			.thenReturn(Optional.empty());
			
			Long userId = 10L;
			String userName = "Roberto Facao";
			String userEmail = "roberto.facao@bestmail.com";
			String documentNumber = "65678860054";
			
			try {
				userUseCase.updateUser(userId, documentNumber, userName, userEmail);
			} catch (Exception e) {
				assertThatExceptionOfType(NotFoundException.class);
				assertThat(e.getMessage()).contains("User not found");
			}
			
		}
		
		@Test
		void givenInvalidNewEmail_thenRefusesUpdateEmail () throws Exception {
			try {
				User userData = new User(10L, new CPF("65678860054"), "Roberto Machado", "roberto.machado@bestmail.com");

				when(userRepository.getUserById(any(Long.class)))
				.thenReturn(Optional.of(userData));

				Long userId = 10L;
				String userName = "Roberto Facao";
				String userEmail = "roberto.facaobestmail.com";
				String documentNumber = "90779778057";

				userUseCase.updateUser(userId, documentNumber, userName, userEmail);
			}catch (Exception e) {
				assertThatExceptionOfType(ValidationException.class);
				assertThat(e.getMessage()).contains("invalid e-mail format");
			}
		}

		@Test
		void givenNewCPF_thenUpdateCPF () throws Exception {
			User userData = new User(10L, new CPF("65678860054"), "Roberto Machado", "roberto.machado@bestmail.com");

			when(userRepository.getUserById(any(Long.class)))
			.thenReturn(Optional.of(userData));

			when(userRepository.updateUser(any(User.class)))
			.thenAnswer(i -> 
			{ 
				Optional<User> user = Optional.of(i.getArgument(0));
				return user;
			});

			Long userId = 10L;
			String documentNumber = "65678860054";

			User newuserData = userUseCase.updateUser(userId, documentNumber, null, null);

			assertThat(newuserData.getId()).isEqualTo(userId);
			assertThat(newuserData.getDocumentNumber().getDocumentNumber()).isEqualTo(documentNumber);
		}

		@Test
		void givenInvalidNewCPF_thenRefusesUpdateCPF () throws Exception {
			try {
				User userData = new User(10L, new CPF("65678860054"), "Roberto Machado", "roberto.machado@bestmail.com");

				when(userRepository.getUserById(any(Long.class)))
				.thenReturn(Optional.of(userData));

				Long userId = 10L;
				String userName = "Roberto Machado";
				String userEmail = "roberto.machado@bestmail.com";
				String documentNumber = "90779778056";

				userUseCase.updateUser(userId, documentNumber, userName, userEmail);
			}catch (Exception e) {
				assertThatExceptionOfType(ValidationException.class);
				assertThat(e.getMessage()).contains("Invalid document number");
			}
		}		
	}

	@Nested
	class DeletarCliente {
		@Test
		void givenId_thenDeleteuser () throws Exception {
			User userData = new User(10L, new CPF("90779778057"), "Roberto Machado", "roberto.machado@bestmail.com");

			when(userRepository.deleteUser(any(Long.class)))
			.thenReturn(true);

			when(userRepository.getUserById(any(Long.class)))
			.thenReturn(Optional.of(userData))
			.thenThrow(NotFoundException.class);

			Boolean deleted = false;

			try {
				Long userId = 10L;
				deleted = userUseCase.deleteUser(userId);
				userUseCase.getUserById(userId);
			} catch (NotFoundException e) {
				assertThat(deleted).isTrue();
			} catch (Exception e) {
				assertThatExceptionOfType(Exception.class);
			}
		}

		@Test
		void givenInexistentId_thenRefusesDeleteuser () throws Exception {

			when(userRepository.deleteUser(any(Long.class)))
			.thenThrow(NotFoundException.class);

			try {
				Long userId = 20L;
				userUseCase.deleteUser(userId);
			} catch (NotFoundException e) {
				assertThatExceptionOfType(NotFoundException.class);
			} 
		}
	}
}
