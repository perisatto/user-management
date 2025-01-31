package com.perisatto.fiapprj.user_management.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import com.perisatto.fiapprj.user_management.infra.gateways.CognitoUserManagement;

@Configuration
public class IAMUserManagementConfig {
	@Autowired
	private Environment env;
	
	@Bean
	CognitoUserManagement cognitoUserManagement(Environment env) {
		return new CognitoUserManagement(env);		
	}
}
