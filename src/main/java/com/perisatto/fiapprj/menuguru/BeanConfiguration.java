package com.perisatto.fiapprj.menuguru;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.perisatto.fiapprj.menuguru.application.domain.service.CustomerService;
import com.perisatto.fiapprj.menuguru.application.port.in.CreateCustomerUseCase;
import com.perisatto.fiapprj.menuguru.application.port.out.CreateCustomerPort;
import com.perisatto.fiapprj.menuguru.application.port.out.LoadCustomerPort;

@Configuration
@ComponentScan(basePackageClasses = MenuguruApplication.class)
public class BeanConfiguration {

	@Bean
	CreateCustomerUseCase createCustomerUseCase(LoadCustomerPort loadCustomerPort) {
		return new CustomerService(loadCustomerPort);
	}
}
