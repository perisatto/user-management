package com.perisatto.fiapprj.menuguru.adapter.in;

import java.net.URI;
import java.util.Properties;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.perisatto.fiapprj.menuguru.adapter.in.dto.CreateCustomerRequestDTO;
import com.perisatto.fiapprj.menuguru.adapter.in.dto.CreateCustomerResponseDTO;
import com.perisatto.fiapprj.menuguru.adapter.in.dto.GetCustomerResponseDTO;
import com.perisatto.fiapprj.menuguru.adapter.in.dto.UpdateCustomerRequestDTO;
import com.perisatto.fiapprj.menuguru.application.domain.model.Customer;
import com.perisatto.fiapprj.menuguru.application.port.in.ManageCustomerUseCase;

@RestController
@RequestMapping("/menuguru/v1")
public class CustomerController {
	
	@Autowired
	private ManageCustomerUseCase manageCustomerUseCase;
	
	@Autowired
	private Properties requestProperties;
	
	@PostMapping(value = "/customers", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CreateCustomerResponseDTO> createUser(@RequestBody CreateCustomerRequestDTO createRequest) throws Exception {
		requestProperties.setProperty("resourcePath", "/customers");
		Customer customer = manageCustomerUseCase.createCustomer(createRequest.getDocumentNumber(), createRequest.getName(), createRequest.getEmail());
		ModelMapper customerMapper = new ModelMapper();
		CreateCustomerResponseDTO response = customerMapper.map(customer, CreateCustomerResponseDTO.class);
		URI location = new URI("/customers/" + response.getId());
		return ResponseEntity.status(HttpStatus.CREATED).location(location).body(response);
	}
	
	@GetMapping(value = "/customersByCPF/{documentNumber}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GetCustomerResponseDTO> get(@PathVariable(value = "documentNumber") String documentNumber) throws Exception {
		requestProperties.setProperty("resourcePath", "/customersByCPF/" + documentNumber);
		Customer customer = manageCustomerUseCase.getCustomerByCPF(documentNumber);
		ModelMapper customerMapper = new ModelMapper();
		GetCustomerResponseDTO response = customerMapper.map(customer, GetCustomerResponseDTO.class);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@GetMapping(value = "/customers/{customerId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GetCustomerResponseDTO> get(@PathVariable(value = "customerId") Long customerId) throws Exception {
		requestProperties.setProperty("resourcePath", "/customers/" + customerId.toString());
		Customer customer = manageCustomerUseCase.getCustomerById(customerId);
		ModelMapper customerMapper = new ModelMapper();
		GetCustomerResponseDTO response = customerMapper.map(customer, GetCustomerResponseDTO.class);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@PatchMapping(value = "/customers/{customerId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GetCustomerResponseDTO> patch(@PathVariable(value = "customerId") Long customerId, @RequestBody UpdateCustomerRequestDTO updateRequest) throws Exception {
		requestProperties.setProperty("resourcePath", "/customers/" + customerId.toString());			
		Customer customer = manageCustomerUseCase.updateCustomer(customerId, updateRequest.getDocumentNumber(), updateRequest.getName(), updateRequest.getEmail());
		ModelMapper customerMapper = new ModelMapper();
		GetCustomerResponseDTO response = customerMapper.map(customer, GetCustomerResponseDTO.class);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@DeleteMapping(value = "/customers/{customerId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> delete(@PathVariable(value = "customerId") Long customerId) throws Exception {
		requestProperties.setProperty("resourcePath", "/customers/" + customerId.toString());			
		manageCustomerUseCase.deleteCustomer(customerId);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
	}
}
