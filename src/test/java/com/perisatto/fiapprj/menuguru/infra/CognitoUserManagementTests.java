package com.perisatto.fiapprj.menuguru.infra;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import java.util.Random;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;

import com.perisatto.fiapprj.menuguru.domain.entities.user.User;
import com.perisatto.fiapprj.menuguru.handler.exceptions.ValidationException;
import com.perisatto.fiapprj.menuguru.infra.gateways.CognitoUserManagement;

@SpringBootTest
@ActiveProfiles(value = "test")
public class CognitoUserManagementTests {
	
    @Autowired
    private Environment env;
	
	@Test
	void givenValidaData_thenCreateUser() throws Exception {	
		CognitoUserManagement cognitoUserManagement = new CognitoUserManagement(env);
		
		Random random = new Random();
		
		User user = new User();
		user.setEmail("teste_" + random.nextInt() + "@testesunitarios.com.br");
		user.setName("Nome Sobrenome");
		user.setId(999999L);
		user.setPassword("00000000000");
		
		cognitoUserManagement.createUser(user);
	}
	
	@Test
	void givenDuplicateData_thenRefusesCreateUser() throws Exception {	
		CognitoUserManagement cognitoUserManagement = new CognitoUserManagement(env);
		
		Random random = new Random();
		
		User user = new User();
		user.setEmail("teste_" + random.nextInt() + "@testesunitarios.com.br");
		user.setName("Nome Sobrenome");
		user.setId(999999L);
		user.setPassword("00000000000");
		
		cognitoUserManagement.createUser(user);
		
		//tries to recreate the existent user
		try {
			cognitoUserManagement.createUser(user);
		} catch (Exception e) {
			assertThatExceptionOfType(ValidationException.class);
		}		
	}
	
	@Test
	void givenInvalidData_thenRefusesCreateUser() throws Exception {	
		CognitoUserManagement cognitoUserManagement = new CognitoUserManagement(env);
		
		Random random = new Random();
		
		User user = new User();
		user.setEmail("teste_" + random.nextInt() + "@testesunitarios.com.br");
		user.setName("Nome Sobrenome");
		user.setId(999999L);
		user.setPassword("0000000000");
		
		try {
			cognitoUserManagement.createUser(user);
		} catch (Exception e) {
			assertThatExceptionOfType(ValidationException.class);
		}
	}
}
