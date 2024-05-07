package com.perisatto.fiapprj.menuguru.application.domain.service;

import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.perisatto.fiapprj.menuguru.application.domain.model.Product;
import com.perisatto.fiapprj.menuguru.application.domain.model.ProductType;
import com.perisatto.fiapprj.menuguru.application.port.in.ManageProductUseCase;
import com.perisatto.fiapprj.menuguru.application.port.out.ManageProductPort;

public class ProductService implements ManageProductUseCase{

	static final Logger logger = LogManager.getLogger(CustomerService.class);
	
	private ManageProductPort manageProductPort;
	
	public ProductService(ManageProductPort manageProductPort) {
		this.manageProductPort = manageProductPort;
	}
	
	@Override
	public Product createProduct(String name, ProductType type, String description, Double price, String image)	throws Exception {
		logger.info("Asked for new product register...");
		Product product = new Product(name, type, description, price, image);
		product = manageProductPort.createProduct(product);
		logger.info("New product register created.");
		return product;
	}

	@Override
	public Product getProduct(Long id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Product updateProduct(String name, ProductType type, String description, Double price, String image)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean deleteProduct(Long id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Product> findAllProducts(Integer limit, Integer page) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
