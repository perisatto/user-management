package com.perisatto.fiapprj.menuguru_customer.infra.gateways;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.env.Environment;

import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProviderClientBuilder;
import com.amazonaws.services.cognitoidp.model.AdminCreateUserRequest;
import com.amazonaws.services.cognitoidp.model.AdminCreateUserResult;
import com.amazonaws.services.cognitoidp.model.AdminSetUserPasswordRequest;
import com.amazonaws.services.cognitoidp.model.AttributeType;
import com.perisatto.fiapprj.menuguru_customer.application.interfaces.UserManagement;
import com.perisatto.fiapprj.menuguru_customer.domain.entities.user.User;
import com.perisatto.fiapprj.menuguru_customer.handler.exceptions.ValidationException;

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
                .withUserAttributes(new AttributeType().withName("email").withValue(username))
                .withUserAttributes(new AttributeType().withName("given_name").withValue(name))
                .withUserAttributes(new AttributeType().withName("custom:id").withValue(id.toString()))
                .withUserAttributes(new AttributeType().withName("email_verified").withValue("true"))
                .withMessageAction("SUPPRESS");
        try {
            AdminCreateUserResult createUserResult = cognitoClient.adminCreateUser(createUserRequest);
            logger.info("User created with success: " + createUserResult.getUser().getUsername());
        } catch (Exception e) {
            logger.error(e.getMessage());            
			throw new ValidationException("cgto-1000", "Error creating customer. Please refer to log application for details.");
        }
        
        AdminSetUserPasswordRequest request = new AdminSetUserPasswordRequest()
                .withUserPoolId(userPoolId)
                .withUsername(username)
                .withPassword(password)
                .withPermanent(true);

        try {
            cognitoClient.adminSetUserPassword(request);
            logger.info("User password set for user " + username);
        } catch (Exception e) {
        	logger.error(e.getMessage());            
			throw new ValidationException("cgto-1000", "Error setting password. Please refer to log application for details.");
        }
                        
        return user;
	}	
}
