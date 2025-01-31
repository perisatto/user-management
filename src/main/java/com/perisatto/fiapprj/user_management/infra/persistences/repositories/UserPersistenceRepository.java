package com.perisatto.fiapprj.user_management.infra.persistences.repositories;


import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.perisatto.fiapprj.user_management.infra.persistences.entities.UserEntity;

public interface UserPersistenceRepository extends JpaRepository<UserEntity, Long> {

	Optional<UserEntity> findByIdUserAndIdUserStatus(Long userId, Long idStatus);

	Optional<UserEntity> findByDocumentNumberAndIdUserStatus(String documentNumber, Long idStatus);

	Page<UserEntity> findByIdUserStatus(Long idStatus, Pageable pageable);
}
