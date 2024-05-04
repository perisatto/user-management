package com.perisatto.fiapprj.menuguru.adapter.in;

import java.net.URI;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.perisatto.fiapprj.menuguru.application.domain.model.Customer;
import com.perisatto.fiapprj.menuguru.application.port.in.ManageCustomerUseCase;

@RestController
@RequestMapping("/menuguru/v1")
public class CustomerController {
	
	@Autowired
	private ManageCustomerUseCase manageCustomerUseCase;
			
	@PostMapping(value = "/customers", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CreateCustomerResponseDTO> createUser(@RequestBody CreateCustomerRequestDTO createRequest) throws Exception {				
		Customer customer = manageCustomerUseCase.createCustomer(createRequest.getDocumentNumber(), createRequest.getName(), createRequest.geteMail());
		ModelMapper customerMapper = new ModelMapper();
		CreateCustomerResponseDTO response = customerMapper.map(customer, CreateCustomerResponseDTO.class);
		URI location = new URI("/customers/" + response.getId());
		return ResponseEntity.status(HttpStatus.CREATED).location(location).body(response);
	}
	
	@GetMapping(value = "/customers/{documentNumber}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GetCustomerResponseDTO> get(@PathVariable(value = "documentNumber") String documentNumber) throws Exception {
		Customer customer = manageCustomerUseCase.getCustomerByCPF(documentNumber);
		ModelMapper customerMapper = new ModelMapper();
		GetCustomerResponseDTO response = customerMapper.map(customer, GetCustomerResponseDTO.class);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
}
