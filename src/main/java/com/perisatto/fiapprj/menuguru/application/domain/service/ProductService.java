package com.perisatto.fiapprj.menuguru.application.domain.service;

import java.util.Optional;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.perisatto.fiapprj.menuguru.application.domain.model.Product;
import com.perisatto.fiapprj.menuguru.application.domain.model.ProductType;
import com.perisatto.fiapprj.menuguru.application.port.in.ManageProductUseCase;
import com.perisatto.fiapprj.menuguru.application.port.out.ManageProductPort;
import com.perisatto.fiapprj.menuguru.handler.exceptions.NotFoundException;
import com.perisatto.fiapprj.menuguru.handler.exceptions.ValidationException;

public class ProductService implements ManageProductUseCase{

	static final Logger logger = LogManager.getLogger(CustomerService.class);

	private ManageProductPort manageProductPort;

	public ProductService(ManageProductPort manageProductPort) {
		this.manageProductPort = manageProductPort;
	}

	@Override
	public Product createProduct(String name, String type, String description, Double price, String image)	throws Exception {
		Product product;
		try {
			logger.info("Asked for new product register...");
			product = new Product(name, ProductType.valueOf(type), description, price, image);
			product = manageProductPort.createProduct(product);
			logger.info("New product register created.");
		} catch (IllegalArgumentException e) {
			if(e.getMessage().contains("No enum constant")) {
				throw new ValidationException("prdt-2001","Invalid product type");
			}else {
				logger.info("Error creating a new product: "+ e.getMessage());
				throw e;
			}
		} catch (NullPointerException e) {
			logger.info("Error creating a new product: "+ e.getMessage());
			throw new ValidationException("prdt-2001","Invalid product type");
		}
		return product;
	}

	@Override
	public Product getProduct(Long id) throws Exception {
		Optional<Product> product = manageProductPort.getProductById(id);
		if(product.isPresent()) {
			return product.get();
		} else {
			throw new NotFoundException("prdt-2002", "Product not found");
		}
	}

	@Override
	public Product updateProduct(String name, String type, String description, Double price, String image)
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
		if(limit==null) {
			limit = 10;
		}
		
		if(page==null) {
			page = 1;
		}
		
		validateFindAll(limit, page);		
		
		Set<Product> findResult = manageProductPort.findAll(limit, page - 1);		
		return findResult;
	}
	
	private void validateFindAll(Integer limit, Integer page) throws Exception {
		if (limit < 0 || limit > 50) {
			String message = "Invalid size parameter. Value must be greater than 0 and less than 50. Actual value: " + limit;
			logger.debug("\"validateFindAll\" | limit validation: " + message);
			throw new ValidationException("prdt-2003", message);			
		}
		
		if (page < 1) {
			String message = "Invalid page parameter. Value must be greater than 0. Actual value: " + page;
			logger.debug("\"validateFindAll\" | offset validation: " + message);
			throw new ValidationException("prdt-2004", message);	
		}
	}

}
