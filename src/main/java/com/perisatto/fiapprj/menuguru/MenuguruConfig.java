package com.perisatto.fiapprj.menuguru;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.perisatto.fiapprj.menuguru.application.domain.service.CustomerService;
import com.perisatto.fiapprj.menuguru.application.port.in.ManageCustomerUseCase;
import com.perisatto.fiapprj.menuguru.application.port.out.ManageCustomerPort;

@Configuration
public class MenuguruConfig {
	
	@Autowired
	private Environment env;
	
	@Bean
	public DataSource dataSource() {
	    DriverManagerDataSource dataSource = new DriverManagerDataSource();

	    dataSource.setDriverClassName(env.getProperty("spring.datasource.driverClassName"));
	    dataSource.setUsername(env.getProperty("spring.datasource.username"));
	    dataSource.setPassword(env.getProperty("spring.datasource.password"));
	    dataSource.setUrl(env.getProperty("spring.datasource.url")); 
	    
	    return dataSource;
	}
	
	@Bean
	ManageCustomerUseCase manageCustomerUseCase(ManageCustomerPort manageCustomerPort) {
		return new CustomerService(manageCustomerPort);
	}
}
