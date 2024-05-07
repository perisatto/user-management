package com.perisatto.fiapprj.menuguru.adapter.out;

import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.perisatto.fiapprj.menuguru.application.domain.model.Product;
import com.perisatto.fiapprj.menuguru.application.port.out.ManageProductPort;

@Component
public class ProductPersistenceAdapter implements ManageProductPort {
	
	private ProductRepository productRepository;
	
	public ProductPersistenceAdapter(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	@Override
	public Product createProduct(Product product) throws Exception {
		ProductMapper productMapper = new ProductMapper();
		ProductJpaEntity productJpaEntity =  productMapper.mapToJpaEntity(product);
		productJpaEntity = productRepository.save(productJpaEntity);
		Product newProduct;
		newProduct = productMapper.mapToDomainEntity(productJpaEntity);
		return newProduct;
	}

	@Override
	public Optional<Product> getProductById(Long id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<Product> updateProduct(Product product) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean deleteProduct(Long id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Product> findAll(Integer limit, Integer offset) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
