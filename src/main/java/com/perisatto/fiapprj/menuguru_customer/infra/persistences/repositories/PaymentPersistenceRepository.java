package com.perisatto.fiapprj.menuguru_customer.infra.persistences.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.perisatto.fiapprj.menuguru_customer.infra.persistences.entities.PaymentDocument;

public interface PaymentPersistenceRepository extends MongoRepository<PaymentDocument, String >{

}
