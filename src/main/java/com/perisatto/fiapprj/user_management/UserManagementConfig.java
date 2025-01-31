package com.perisatto.fiapprj.user_management;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.perisatto.fiapprj.user_management.application.interfaces.IAMUserManagement;
import com.perisatto.fiapprj.user_management.application.interfaces.UserManagementRepository;
import com.perisatto.fiapprj.user_management.application.usecases.UserManagementUseCase;
import com.perisatto.fiapprj.user_management.infra.gateways.UserManagementRepositoyJpa;
import com.perisatto.fiapprj.user_management.infra.gateways.mappers.UserMapper;
import com.perisatto.fiapprj.user_management.infra.persistences.repositories.UserPersistenceRepository;

@Configuration
public class UserManagementConfig {
	
	@Autowired
	private Environment env;
	
	@Bean
	UserManagementUseCase userManagementUseCase(UserManagementRepository customerRepository, IAMUserManagement userManagement) {
		return new UserManagementUseCase(customerRepository, userManagement);
	}	
	
	@Bean
	UserManagementRepositoyJpa userManagementJpaRepository(UserPersistenceRepository customerPersistenceRepository, UserMapper customerMapper) {
		return new UserManagementRepositoyJpa(customerPersistenceRepository, customerMapper);
	}
	
	@Bean
	UserMapper customerMapper() {
		return new UserMapper();
	}
	
	@Bean
	public DataSource dataSource() {
	    DriverManagerDataSource dataSource = new DriverManagerDataSource();

	    dataSource.setDriverClassName(env.getProperty("spring.datasource.driverClassName"));
	    dataSource.setUsername(env.getProperty("spring.datasource.username"));
	    dataSource.setPassword(env.getProperty("spring.datasource.password"));
	    dataSource.setUrl(env.getProperty("spring.datasource.url")); 
	    
	    return dataSource;
	}
}
