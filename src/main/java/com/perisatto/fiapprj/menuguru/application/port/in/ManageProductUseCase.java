package com.perisatto.fiapprj.menuguru.application.port.in;

import java.util.Set;

import com.perisatto.fiapprj.menuguru.application.domain.model.Product;
import com.perisatto.fiapprj.menuguru.application.domain.model.ProductType;

public interface ManageProductUseCase {
	Product createProduct(String name, ProductType type, String description, Double price, String image) throws Exception;
	
	Product getProduct(Long id) throws Exception;
	
	Product updateProduct(String name, ProductType type, String description, Double price, String image) throws Exception;
	
	Boolean deleteProduct(Long id) throws Exception;
	
	Set<Product> findAllProducts(Integer limit, Integer page) throws Exception;
}
