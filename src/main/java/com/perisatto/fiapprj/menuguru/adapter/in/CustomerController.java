package com.perisatto.fiapprj.menuguru.adapter.in;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.perisatto.fiapprj.menuguru.application.port.in.CreateCustomerUseCase;

@RestController
@RequestMapping("/menuguru/v1")
public class CustomerController {
	
	@Autowired
	private CreateCustomerUseCase createCustomerUseCase;
			
	@PostMapping(value = "/customers", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public void createUser(@RequestBody CreateCustomerRequestDTO createRequest) {				
		createCustomerUseCase.createCustomer(createRequest.getDocumentNumber(), createRequest.getName(), createRequest.geteMail());
	}
}
