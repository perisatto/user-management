package com.perisatto.fiapprj.user_management.application.interfaces;

import java.util.Optional;
import java.util.Set;

import com.perisatto.fiapprj.user_management.domain.entities.user.CPF;
import com.perisatto.fiapprj.user_management.domain.entities.user.User;

public interface UserManagementRepository {

	User createUser(User user) throws Exception;
	
	Optional<User> getUserByCPF(CPF userDocument) throws Exception;
	
	Optional<User> getUserById(Long userId) throws Exception;
	
	Optional<User> updateUser(User user) throws Exception;
	
	Boolean deleteUser(Long userId) throws Exception;

	Set<User> findAll(Integer limit, Integer offset) throws Exception;
}
