package com.perisatto.fiapprj.user_management.infra.gateways.mappers;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;

import com.perisatto.fiapprj.user_management.domain.entities.user.CPF;
import com.perisatto.fiapprj.user_management.domain.entities.user.User;
import com.perisatto.fiapprj.user_management.infra.persistences.entities.UserEntity;

public class UserMapper {
	
	private ModelMapper modelMapper;
	
	public User mapToDomainEntity(UserEntity customer) throws Exception {
		CPF documentNumber = new CPF(customer.getDocumentNumber());
		User customerDomainEntity = new User(customer.getIdUser(), documentNumber, customer.getName(), customer.geteMail());
		return customerDomainEntity;
	}
	
	public UserEntity mapToJpaEntity(User customer) {
		this.modelMapper = new ModelMapper();
		
		TypeMap<User, UserEntity> propertyMapper = this.modelMapper.createTypeMap(User.class, UserEntity.class);
		propertyMapper.addMapping(User::getId, UserEntity::setIdUser);
		propertyMapper.addMapping(User::getName, UserEntity::setName);
		propertyMapper.addMapping(User::getEmail, UserEntity::seteMail);
		
		UserEntity customerJpaEntity = modelMapper.map(customer, UserEntity.class);
		customerJpaEntity.setDocumentNumber(customer.getDocumentNumber().getDocumentNumber());
		return customerJpaEntity;
		
	}
}
