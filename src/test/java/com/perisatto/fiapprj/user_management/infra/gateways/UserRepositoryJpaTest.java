package com.perisatto.fiapprj.user_management.infra.gateways;

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

import com.perisatto.fiapprj.user_management.domain.entities.user.CPF;
import com.perisatto.fiapprj.user_management.domain.entities.user.User;
import com.perisatto.fiapprj.user_management.infra.gateways.UserManagementRepositoyJpa;
import com.perisatto.fiapprj.user_management.infra.gateways.mappers.UserMapper;
import com.perisatto.fiapprj.user_management.infra.persistences.entities.UserEntity;
import com.perisatto.fiapprj.user_management.infra.persistences.repositories.UserPersistenceRepository;

@ActiveProfiles(value = "test")
public class UserRepositoryJpaTest {

	private UserManagementRepositoyJpa userRepositoyJpa;
	
	@Mock
	private UserPersistenceRepository userRepository;
	
	private UserMapper userMapper;
	
	AutoCloseable openMocks;
	
	@BeforeEach
	void setUp() {
		openMocks = MockitoAnnotations.openMocks(this);
		userMapper = new UserMapper();
		userRepositoyJpa = new UserManagementRepositoyJpa(userRepository, userMapper);
	}
	
	@AfterEach
	void tearDown() throws Exception {
		openMocks.close();
	}
	
	
	@Test
	void givenValidData_thenSavesUser() throws Exception {
		
		UserEntity userEntity = new UserEntity();
		
		userEntity.setDocumentNumber("67505036084");
		userEntity.seteMail("robertomachado@bestmail.com");
		userEntity.setIdUser(10L);
		userEntity.setIdUserStatus(1L);
		userEntity.setName("Roberto Machado");
		
		when(userRepository.save(any()))
		.thenReturn(userEntity);
		
		String name = "Roberto Machado";
		String email = "robertomachado@bestmail.com";
		String documentNumber = "67505036084";
		
		CPF cpf = new CPF(documentNumber);
		
		User user = new User(null, cpf, name, email);
		
		userRepositoyJpa.createUser(user);
		
		verify(userRepository, times(1)).save(any());
	}
	
	@Test
	void givenValidCPF_thenRetrievesUser() throws Exception {
		UserEntity userEntity = new UserEntity();
		
		userEntity.setDocumentNumber("67505036084");
		userEntity.seteMail("robertomachado@bestmail.com");
		userEntity.setIdUser(10L);
		userEntity.setIdUserStatus(1L);
		userEntity.setName("Roberto Machado");
		
		when(userRepository.findByDocumentNumberAndIdUserStatus(any(String.class), any(Long.class)))
		.thenReturn(Optional.of(userEntity));
		
		String documentNumber = "67505036084";
		
		CPF cpf = new CPF(documentNumber);
		
		userRepositoyJpa.getUserByCPF(cpf);
		
		verify(userRepository, times(1)).findByDocumentNumberAndIdUserStatus(any(String.class), any(Long.class));
	}
	
	@Test
	void givenInexistentCPF_thenRefusesRetrieveUser() throws Exception {
	
		when(userRepository.findByDocumentNumberAndIdUserStatus(any(String.class), any(Long.class)))
		.thenReturn(Optional.empty());
		
		String documentNumber = "67505036084";
		
		CPF cpf = new CPF(documentNumber);
		
		userRepositoyJpa.getUserByCPF(cpf);
		
		verify(userRepository, times(1)).findByDocumentNumberAndIdUserStatus(any(String.class), any(Long.class));
	}
	
	@Test
	void givenValidId_thenRetrieveUser() throws Exception {
	
		UserEntity userEntity = new UserEntity();
		
		userEntity.setDocumentNumber("67505036084");
		userEntity.seteMail("robertomachado@bestmail.com");
		userEntity.setIdUser(10L);
		userEntity.setIdUserStatus(1L);
		userEntity.setName("Roberto Machado");
		
		when(userRepository.findByIdUserAndIdUserStatus(any(Long.class), any(Long.class)))
		.thenReturn(Optional.of(userEntity));
		
		Long userId = 10L;
		
		userRepositoyJpa.getUserById(userId);
		
		verify(userRepository, times(1)).findByIdUserAndIdUserStatus(any(Long.class), any(Long.class));
	}
	
	@Test
	void givenInexistentId_thenRefusesRetrieveUser() throws Exception {
	
		when(userRepository.findByIdUserAndIdUserStatus(any(Long.class), any(Long.class)))
		.thenReturn(Optional.empty());
		
		Long userId = 10L;
		
		userRepositoyJpa.getUserById(userId);
		
		verify(userRepository, times(1)).findByIdUserAndIdUserStatus(any(Long.class), any(Long.class));
	}
	
	@Test
	void listAllUsers() throws Exception {
		when(userRepository.findByIdUserStatus(any(Long.class), any()))
		.thenAnswer(i -> {
			
			List<UserEntity> userList = new ArrayList<>();
			
			UserEntity userEntity = new UserEntity();
			
			userEntity.setDocumentNumber("67505036084");
			userEntity.seteMail("robertomachado@bestmail.com");
			userEntity.setIdUser(10L);
			userEntity.setIdUserStatus(1L);
			userEntity.setName("Roberto Machado");
			
			userList.add(userEntity);
			
			UserEntity userEntity2 = new UserEntity();
			
			userEntity2.setDocumentNumber("67505036084");
			userEntity2.seteMail("robertomachado@bestmail.com");
			userEntity2.setIdUser(20L);
			userEntity2.setIdUserStatus(1L);
			userEntity2.setName("Roberto Machado");
			
			userList.add(userEntity2);
			
			Page<UserEntity> users = new PageImpl<>(userList);
			
			return users;
		});
		
		userRepositoyJpa.findAll(50, 1);
		
		verify(userRepository, times(1)).findByIdUserStatus(any(Long.class), any());
	}
	
	@Test
	void givenValidData_UpdatesUser() throws Exception {
		UserEntity userEntity = new UserEntity();
		
		userEntity.setDocumentNumber("67505036084");
		userEntity.seteMail("robertomachado@bestmail.com");
		userEntity.setIdUser(10L);
		userEntity.setIdUserStatus(1L);
		userEntity.setName("Roberto Machado");
		
		when(userRepository.save(any(UserEntity.class)))
		.thenReturn(userEntity);
		
		String name = "Roberto Machado";
		String email = "robertomachado@bestmail.com";
		String documentNumber = "67505036084";
		
		CPF cpf = new CPF(documentNumber);
		
		User user = new User(null, cpf, name, email);
		
		userRepositoyJpa.updateUser(user);
		
		verify(userRepository, times(1)).save(any(UserEntity.class));
	}
	
	@Test
	void givenValidId_thenDeletesUser() throws Exception {
		UserEntity userEntity = new UserEntity();
		
		userEntity.setDocumentNumber("67505036084");
		userEntity.seteMail("robertomachado@bestmail.com");
		userEntity.setIdUser(10L);
		userEntity.setIdUserStatus(1L);
		userEntity.setName("Roberto Machado");
		
		when(userRepository.findById(any(Long.class)))
		.thenReturn(Optional.of(userEntity));
		
		Boolean deleted = userRepositoyJpa.deleteUser(10L);
		
		assertThat(deleted).isTrue();
	}
	
	@Test
	void givenInvalidId_thenDeletesUser() throws Exception {		
		when(userRepository.findById(any(Long.class)))
		.thenReturn(Optional.empty());
		
		Boolean deleted = userRepositoyJpa.deleteUser(10L);
		
		assertThat(deleted).isFalse();
	}
}
