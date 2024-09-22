package com.perisatto.fiapprj.menuguru.infra.gateways;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.env.Environment;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProviderClientBuilder;
import com.amazonaws.services.cognitoidp.model.AdminCreateUserRequest;
import com.amazonaws.services.cognitoidp.model.AdminCreateUserResult;
import com.amazonaws.services.cognitoidp.model.AttributeType;
import com.perisatto.fiapprj.menuguru.application.interfaces.UserManagement;
import com.perisatto.fiapprj.menuguru.domain.entities.user.User;

public class CognitoUserManagement implements UserManagement {

	static final Logger logger = LogManager.getLogger(CognitoUserManagement.class);
	
	private final Environment env;

	public CognitoUserManagement(Environment env) {
		this.env = env;		
	}	
	
	@Override
	public User createUser(User user) throws Exception {
		String userPoolId = env.getProperty("spring.cognito.userPoolId");
        String username = user.getEmail();
        String password = user.getPassword();
        String name = user.getName();
        Long id = user.getId();
		
		AWSCognitoIdentityProvider cognitoClient = AWSCognitoIdentityProviderClientBuilder.defaultClient();
		AdminCreateUserRequest createUserRequest = new AdminCreateUserRequest()
                .withUserPoolId(userPoolId)
                .withUsername(username)
                .withTemporaryPassword("01010101010")
                .withUserAttributes(new AttributeType().withName("email").withValue(username))
                .withUserAttributes(new AttributeType().withName("given_name").withValue(name))
                .withUserAttributes(new AttributeType().withName("custom:id").withValue(id.toString()))
                .withMessageAction("SUPPRESS");
        try {
            AdminCreateUserResult createUserResult = cognitoClient.adminCreateUser(createUserRequest);
            System.out.println("Usuário criado com sucesso: " + createUserResult.getUser().getUsername());
        } catch (Exception e) {
            e.printStackTrace();
        }
                        
        return user;
	}	
}
