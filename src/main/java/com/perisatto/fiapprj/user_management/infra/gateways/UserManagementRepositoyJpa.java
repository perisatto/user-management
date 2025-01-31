package com.perisatto.fiapprj.user_management.infra.gateways;

import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.perisatto.fiapprj.user_management.application.interfaces.UserManagementRepository;
import com.perisatto.fiapprj.user_management.domain.entities.user.CPF;
import com.perisatto.fiapprj.user_management.domain.entities.user.User;
import com.perisatto.fiapprj.user_management.infra.gateways.mappers.UserMapper;
import com.perisatto.fiapprj.user_management.infra.persistences.entities.UserEntity;
import com.perisatto.fiapprj.user_management.infra.persistences.repositories.UserPersistenceRepository;

public class UserManagementRepositoyJpa implements UserManagementRepository {
	
	private final UserPersistenceRepository userRepository;
	private final UserMapper userMapper;

	public UserManagementRepositoyJpa(UserPersistenceRepository userRepository, UserMapper userMapper) {
		this.userRepository = userRepository;
		this.userMapper = userMapper;
	}

	public Optional<User> getUserByCPF(CPF userDocument) throws Exception {
		User user;

		Optional<UserEntity> userJpaEntity = userRepository.findByDocumentNumberAndIdUserStatus(userDocument.getDocumentNumber(), 1L);
		if(userJpaEntity.isPresent()) {				
			user = userMapper.mapToDomainEntity(userJpaEntity.get());
		}else {
			return Optional.empty();
		}

		return Optional.of(user);
	}
	
	public Optional<User> getUserById(Long userId) throws Exception{
		User user;

		Optional<UserEntity> userJpaEntity = userRepository.findByIdUserAndIdUserStatus(userId, 1L);
		if(userJpaEntity.isPresent()) {				
			user = userMapper.mapToDomainEntity(userJpaEntity.get());
		}else {
			return Optional.empty();
		}

		return Optional.of(user);
	}

	@Override
	public User createUser(User user) throws Exception {
		UserEntity userJpaEntity =  userMapper.mapToJpaEntity(user);
		userJpaEntity.setIdUserStatus(1L);
		userJpaEntity = userRepository.save(userJpaEntity);
		User newUser;
		newUser = userMapper.mapToDomainEntity(userJpaEntity);
		return newUser;
	}

	@Override
	public Optional<User> updateUser(User user) throws Exception {
		UserEntity userJpaEntity = userMapper.mapToJpaEntity(user);
		userJpaEntity.setIdUserStatus(1L);
		userJpaEntity = userRepository.save(userJpaEntity);
		User updatedUser = userMapper.mapToDomainEntity(userJpaEntity);
		return Optional.of(updatedUser);
	}

	@Override
	public Boolean deleteUser(Long userId) throws Exception {
		Optional<UserEntity> user = userRepository.findById(userId);
		if(user.isPresent()) {
			user.get().setIdUserStatus(2L);
			userRepository.save(user.get());
			return true;
		} else {
			return false;
		}
	}

	@Override
	public Set<User> findAll(Integer limit, Integer page) throws Exception {
		Pageable pageable = PageRequest.of(page, limit, Sort.by("idUser"));
		Page<UserEntity> users = userRepository.findByIdUserStatus(1L, pageable);
		Set<User> usersSet = new LinkedHashSet<User>();
		
		for (UserEntity user : users) {
			User retrievedUser = new User(user.getIdUser(), new CPF(user.getDocumentNumber()), user.getName(), user.geteMail());
			usersSet.add(retrievedUser);
		}
		return usersSet;
	}

}
