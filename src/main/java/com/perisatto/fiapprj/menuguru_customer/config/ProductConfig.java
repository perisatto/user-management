package com.perisatto.fiapprj.menuguru_customer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.perisatto.fiapprj.menuguru_customer.application.interfaces.ProductRepository;
import com.perisatto.fiapprj.menuguru_customer.application.usecases.ProductUseCase;
import com.perisatto.fiapprj.menuguru_customer.infra.gateways.ProductRepositoryJpa;
import com.perisatto.fiapprj.menuguru_customer.infra.gateways.mappers.ProductMapper;
import com.perisatto.fiapprj.menuguru_customer.infra.persistences.repositories.ProductPersistenceRepository;

@Configuration
public class ProductConfig {

	@Bean
	ProductUseCase productUseCase(ProductRepository productRepository) {
		return new ProductUseCase(productRepository);
	}	
	
	@Bean
	ProductRepositoryJpa productJpaRepository(ProductPersistenceRepository productPersistenceRepository, ProductMapper productMapper) {
		return new ProductRepositoryJpa(productPersistenceRepository, productMapper);
	}
	
	@Bean
	ProductMapper productMapper() {
		return new ProductMapper();
	}
}
