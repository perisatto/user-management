package com.perisatto.fiapprj.menuguru;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.perisatto.fiapprj.menuguru.application.domain.service.CustomerService;
import com.perisatto.fiapprj.menuguru.application.port.in.ManageCustomerUseCase;
import com.perisatto.fiapprj.menuguru.application.port.out.CreateCustomerPort;
import com.perisatto.fiapprj.menuguru.application.port.out.LoadCustomerPort;

@Configuration
@ComponentScan(basePackageClasses = MenuguruApplication.class)
public class BeanConfiguration {

	@Bean
	ManageCustomerUseCase manageCustomerUseCase(LoadCustomerPort loadCustomerPort, CreateCustomerPort createCustomerPort) {
		return new CustomerService(loadCustomerPort, createCustomerPort);
	}
}
