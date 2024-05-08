package com.perisatto.fiapprj.menuguru.adapter.out;

import java.util.Base64;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Component;

import com.perisatto.fiapprj.menuguru.application.domain.model.Customer;
import com.perisatto.fiapprj.menuguru.application.domain.model.Product;
import com.perisatto.fiapprj.menuguru.application.domain.model.ProductType;
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
		Product product;

		Optional<ProductJpaEntity> productJpaEntity = productRepository.findById(id);
		if(productJpaEntity.isPresent()) {
			ProductMapper productMapper = new ProductMapper();				
			product = productMapper.mapToDomainEntity(productJpaEntity.get());
		}else {
			return Optional.empty();
		}

		return Optional.of(product);
	}

	@Override
	public Optional<Product> updateProduct(Product product) throws Exception {
		ProductMapper productMapper = new ProductMapper();
		ProductJpaEntity productJpaEntity = productMapper.mapToJpaEntity(product);
		productJpaEntity = productRepository.save(productJpaEntity);
		Product updatedProduct = productMapper.mapToDomainEntity(productJpaEntity);
		return Optional.of(updatedProduct);
	}

	@Override
	public Boolean deleteProduct(Long id) throws Exception {
		productRepository.deleteById(id);
		return true;
	}

	@Override
	public Set<Product> findAll(Integer limit, Integer offset, String type) throws Exception {	
		
		ProductType productType = null;
		
		if(type != null) {		
			productType = ProductType.valueOf(type);
		}
		
		Pageable pageable = PageRequest.of(offset, limit, Sort.by("idProduct"));
		Page<ProductJpaEntity> products = productRepository.findAll(pageable);
		Set<Product> productSet = new LinkedHashSet<Product>();
		
		for (ProductJpaEntity product : products) {
			String base64Image = Base64.getEncoder().encodeToString(product.getImage());
			Product retrievedProduct = new Product(product.getName(), ProductType.values()[(int) (product.getIdProductType() - 1)], product.getDescription(), product.getPrice(), base64Image);
			retrievedProduct.setId(product.getIdProduct());
			
			if(type==null) {
				productSet.add(retrievedProduct);
			} else {
				if(retrievedProduct.getProductType()==productType) {
					productSet.add(retrievedProduct);
				}
			}
		}
		return productSet;
	}

}
