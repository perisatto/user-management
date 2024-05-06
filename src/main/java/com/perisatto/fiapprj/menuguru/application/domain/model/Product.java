package com.perisatto.fiapprj.menuguru.application.domain.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.perisatto.fiapprj.menuguru.handler.exceptions.ValidationException;


public class Product {

	static final Logger logger = LogManager.getLogger(Product.class);
	
	private String name;
	private ProductType type;
	private String description;
	private Double price;
	private String image;

	private String messageValidation;

	public Product(String name, ProductType type, String description, Double price, String image) throws Exception {
		if(!validate(name, description, price)) {
			logger.warn(messageValidation);
			throw new ValidationException("prdt-1001", messageValidation);
		}

		this.name = name;
		this.type = type;
		this.description = description;
		this.price = price;
		this.image = image;		
	}

	private boolean validate(String name, String description, Double price) {
		messageValidation = "Error validating product data: ";		
		Boolean valid = true;
		
		if ((name == null) || (name.isEmpty()) || (name.isBlank())) {
			logger.info("Error validating product data: null or equals zero price");
			messageValidation = messageValidation + "empty, null or blank name |";
			valid = false;			
		}

		if ((description == null) || (description.isEmpty()) || (description.isBlank())) {
			logger.info("Error validating product data: null or equals zero price");
			messageValidation = messageValidation + "empty, null or blank description |";
			valid = false;			
		} else if(description.length() > 250) {
			logger.info("Error validating product data: null or equals zero price");
			messageValidation = messageValidation + "description too large (greater than 250 caracters) |";
			valid = false;
		}
		
		if((price == null) || (price == 0.0)) {
				logger.info("Error validating product data: null or equals zero price");
				messageValidation = messageValidation + "null or equals zero price |";
				valid = false;			
		}

		return valid;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ProductType getProductType() {
		return this.type;
	}

	public void setType(ProductType type) {
		this.type = type;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getPrice() {		
		return this.price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

}
