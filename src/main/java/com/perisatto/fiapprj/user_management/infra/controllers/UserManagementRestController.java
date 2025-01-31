package com.perisatto.fiapprj.user_management.infra.controllers;

import java.net.URI;
import java.util.Properties;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.perisatto.fiapprj.user_management.application.usecases.UserManagementUseCase;
import com.perisatto.fiapprj.user_management.domain.entities.user.User;
import com.perisatto.fiapprj.user_management.infra.controllers.dtos.CreateUserRequestDTO;
import com.perisatto.fiapprj.user_management.infra.controllers.dtos.CreateUserResponseDTO;
import com.perisatto.fiapprj.user_management.infra.controllers.dtos.GetUserListResponseDTO;
import com.perisatto.fiapprj.user_management.infra.controllers.dtos.GetUserResponseDTO;
import com.perisatto.fiapprj.user_management.infra.controllers.dtos.UpdateUserRequestDTO;

@RestController
public class UserManagementRestController {
	
	private final UserManagementUseCase userManagementUseCase;
	private final Properties requestProperties;

	public UserManagementRestController(UserManagementUseCase userManagementUseCase, Properties requestProperties) {
		this.userManagementUseCase = userManagementUseCase;
		this.requestProperties = requestProperties;
	}
	
	@PostMapping(value = "/users", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CreateUserResponseDTO> createUser(@RequestBody CreateUserRequestDTO createRequest) throws Exception {
		requestProperties.setProperty("resourcePath", "/users");
		
		User user = userManagementUseCase.createUser(createRequest.getDocumentNumber(), createRequest.getName(), createRequest.getEmail());		
		ModelMapper userMapper = new ModelMapper();
		CreateUserResponseDTO response = userMapper.map(user, CreateUserResponseDTO.class);
		URI location = new URI("/users/" + response.getId());
		return ResponseEntity.status(HttpStatus.CREATED).location(location).body(response);
	}
	
	@GetMapping(value = "/usersByCPF/{documentNumber}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GetUserResponseDTO> get(@PathVariable(value = "documentNumber") String documentNumber) throws Exception {
		requestProperties.setProperty("resourcePath", "/usersByCPF/" + documentNumber);
		User user = userManagementUseCase.getUserByCPF(documentNumber);
		ModelMapper userMapper = new ModelMapper();
		GetUserResponseDTO response = userMapper.map(user, GetUserResponseDTO.class);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@GetMapping(value = "/users/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GetUserResponseDTO> get(@PathVariable(value = "userId") Long userId) throws Exception {
		requestProperties.setProperty("resourcePath", "/users/" + userId.toString());
		User user = userManagementUseCase.getUserById(userId);
		ModelMapper userMapper = new ModelMapper();
		GetUserResponseDTO response = userMapper.map(user, GetUserResponseDTO.class);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@GetMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GetUserListResponseDTO> getAll(@RequestParam(value = "_page", required = true) Integer page, @RequestParam(value = "_size", required = true) Integer size) throws Exception {
		requestProperties.setProperty("resourcePath", "/users");
		Set<User> user = userManagementUseCase.findAllUsers(size, page);
		GetUserListResponseDTO response = new GetUserListResponseDTO();
		response.setContent(user, page, size);		
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@PatchMapping(value = "/users/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GetUserResponseDTO> patch(@PathVariable(value = "userId") Long userId, @RequestBody UpdateUserRequestDTO updateRequest) throws Exception {
		requestProperties.setProperty("resourcePath", "/users/" + userId.toString());			
		User user = userManagementUseCase.updateUser(userId, updateRequest.getDocumentNumber(), updateRequest.getName(), updateRequest.getEmail());
		ModelMapper userMapper = new ModelMapper();
		GetUserResponseDTO response = userMapper.map(user, GetUserResponseDTO.class);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@DeleteMapping(value = "/users/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> delete(@PathVariable(value = "userId") Long userId) throws Exception {
		requestProperties.setProperty("resourcePath", "/users/" + userId.toString());			
		userManagementUseCase.deleteUser(userId);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
	}
}
