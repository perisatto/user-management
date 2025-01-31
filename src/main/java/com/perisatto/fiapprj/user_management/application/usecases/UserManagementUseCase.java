package com.perisatto.fiapprj.user_management.application.usecases;

import java.util.Optional;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.perisatto.fiapprj.user_management.application.interfaces.UserManagementRepository;
import com.perisatto.fiapprj.user_management.application.interfaces.IAMUserManagement;
import com.perisatto.fiapprj.user_management.domain.entities.user.CPF;
import com.perisatto.fiapprj.user_management.domain.entities.user.IAMUser;
import com.perisatto.fiapprj.user_management.domain.entities.user.User;
import com.perisatto.fiapprj.user_management.handler.exceptions.NotFoundException;
import com.perisatto.fiapprj.user_management.handler.exceptions.ValidationException;

public class UserManagementUseCase {
	
	static final Logger logger = LogManager.getLogger(UserManagementUseCase.class);	
	private final UserManagementRepository userManagementRepository;
	private final IAMUserManagement iamUserManagement;

	public UserManagementUseCase(UserManagementRepository userRepository, IAMUserManagement userManagement) {
		this.userManagementRepository = userRepository;
		this.iamUserManagement = userManagement;
	}

	public User createUser(String documentNumber, String name, String email) throws Exception {
		logger.info("Asked for new user register...");
		User newUser;

		CPF newUserDocumentNumber = new CPF(documentNumber);
		String newUserName = name;
		String newUserEmail = email;

		newUser = new User(null, newUserDocumentNumber, newUserName, newUserEmail);

		if(userManagementRepository.getUserByCPF(newUser.getDocumentNumber()).isEmpty()) {
			logger.info("User inexistent... creating new user register.");
			newUser = userManagementRepository.createUser(newUser);
			logger.info("New user register created.");
			
			IAMUser iamUser = new IAMUser();
			
			iamUser.setEmail(newUser.getEmail());
			iamUser.setId(newUser.getId());
			iamUser.setName(newUser.getName());
			iamUser.setPassword(newUser.getDocumentNumber().getDocumentNumber());
			
			iamUserManagement.createUser(iamUser);
			
			return newUser;
		}else {
			logger.info("User already exists. New user register aborted.");
			throw new ValidationException("cstm-1001", "User already exists");
		}	
	}
	
	
	public User getUserByCPF(String documentNumber) throws Exception {
		CPF userDocument;
		userDocument = new CPF(documentNumber);			
		Optional<User> user = userManagementRepository.getUserByCPF(userDocument);
		if(user.isPresent()) {
			return user.get();
		} else {
			throw new NotFoundException("cstm-1003", "User not found");
		}	
	}

	public User getUserById(Long userId) throws Exception {	
		Optional<User> user = userManagementRepository.getUserById(userId);
		if(user.isPresent()) {
			return user.get();
		} else {
			throw new NotFoundException("cstm-1004", "User not found");
		}
	}

	public User updateUser(Long userId, String documentNumber, String userName, String userEmail) throws Exception {	
		Optional<User> oldUserData = userManagementRepository.getUserById(userId);
		if(oldUserData.isPresent()) {
			
			CPF newDocumentNumber;
			String newUserName;
			String newUserEmail;
			
			if(documentNumber != null) {
				newDocumentNumber = new CPF(documentNumber);
			} else {
				newDocumentNumber = oldUserData.get().getDocumentNumber();
			}
			
			if(userName != null) {
				newUserName = userName;
			} else {
				newUserName = oldUserData.get().getName();
			}

			if(userEmail != null) {
				newUserEmail = userEmail;
			} else {
				newUserEmail = oldUserData.get().getEmail();
			}
			
			User newUserData = new User(userId, newDocumentNumber, newUserName, newUserEmail);
			
			Optional<User> updatedUserData = userManagementRepository.updateUser(newUserData);
			if(updatedUserData.isEmpty()) {
				logger.error("Error while updating user data... user not found during update action.");
				throw new Exception("Error while updating user data. Please refer to application log for details.");
			}
			return updatedUserData.get();
		}else {
			throw new NotFoundException("cstm-1004", "User not found");
		}
	}

	public Boolean deleteUser(Long userId) throws Exception {
		Boolean deleted = false;
		Optional<User> user = userManagementRepository.getUserById(userId);		
		if(user.isPresent()) {
			deleted = userManagementRepository.deleteUser(userId);
			logger.warn("User register deleted: id=" + user.get().getId() + " | documentNumber=" + user.get().getDocumentNumber().getDocumentNumber());
		} else {
			throw new NotFoundException("cstm-1005", "User not found");
		}
		return deleted;
	}

	public Set<User> findAllUsers(Integer limit, Integer page) throws Exception {
		if(limit==null) {
			limit = 10;
		}
		
		if(page==null) {
			page = 1;
		}
		
		validateFindAll(limit, page);		
		
		Set<User> findResult = userManagementRepository.findAll(limit, page - 1);		
		return findResult;
	}	
	
	private void validateFindAll(Integer limit, Integer page) throws Exception {
		if (limit < 0 || limit > 50) {
			String message = "Invalid size parameter. Value must be greater than 0 and less than 50. Actual value: " + limit;
			logger.debug("\"validateFindAll\" | limit validation: " + message);
			throw new ValidationException("cstm-1006", message);			
		}
		
		if (page < 1) {
			String message = "Invalid page parameter. Value must be greater than 0. Actual value: " + page;
			logger.debug("\"validateFindAll\" | offset validation: " + message);
			throw new ValidationException("cstm-1007", message);	
		}
	}	
}
