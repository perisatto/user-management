package com.perisatto.fiapprj.menuguru.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import com.perisatto.fiapprj.menuguru.infra.gateways.CognitoUserManagement;

@Configuration
public class UserManagementConfig {
	@Autowired
	private Environment env;
	
	@Bean
	CognitoUserManagement cognitoUserManagement(Environment env) {
		return new CognitoUserManagement(env);		
	}
}
