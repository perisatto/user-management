package com.perisatto.fiapprj.menuguru.adapter.in;

import java.net.URI;
import java.util.Properties;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.perisatto.fiapprj.menuguru.adapter.in.dto.CreateProductRequestDTO;
import com.perisatto.fiapprj.menuguru.adapter.in.dto.CreateProductResponseDTO;
import com.perisatto.fiapprj.menuguru.application.domain.model.Product;
import com.perisatto.fiapprj.menuguru.application.domain.model.ProductType;
import com.perisatto.fiapprj.menuguru.application.port.in.ManageProductUseCase;

@RestController
@RequestMapping("/menuguru/v1")
public class ProductController {
	
	
	@Autowired
	private ManageProductUseCase manageProductUseCase;
	
	@Autowired
	private Properties requestProperties;
	
	@PostMapping(value = "/products", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CreateProductResponseDTO> createUser(@RequestBody CreateProductRequestDTO createRequest) throws Exception {
		requestProperties.setProperty("resourcePath", "/products");
		Product product = manageProductUseCase.createProduct(createRequest.getName(), ProductType.valueOf(createRequest.getProductType()), 
				createRequest.getDescription(), createRequest.getPrice(), createRequest.getImage());
		ModelMapper productMapper = new ModelMapper();
		CreateProductResponseDTO response = productMapper.map(product, CreateProductResponseDTO.class);
		URI location = new URI("/products/" + response.getId());
		return ResponseEntity.status(HttpStatus.CREATED).location(location).body(response);
	}
}
