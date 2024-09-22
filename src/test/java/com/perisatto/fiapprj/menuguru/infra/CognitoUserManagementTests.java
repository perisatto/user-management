package com.perisatto.fiapprj.menuguru.infra;

import java.util.Random;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;

import com.perisatto.fiapprj.menuguru.domain.entities.user.User;
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
}
