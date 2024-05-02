package com.perisatto.fiapprj.menuguru.adapter.out;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerJpaEntity, Long> {
	
	Optional<CustomerJpaEntity> findByDocumentNumber(String documentNumber);
}
