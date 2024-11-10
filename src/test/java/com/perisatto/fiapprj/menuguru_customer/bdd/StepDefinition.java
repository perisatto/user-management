package com.perisatto.fiapprj.menuguru_customer.bdd;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.CoreMatchers.equalTo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import com.perisatto.fiapprj.menuguru_customer.application.interfaces.UserManagement;
import com.perisatto.fiapprj.menuguru_customer.infra.controllers.dtos.CreateCustomerRequestDTO;
import com.perisatto.fiapprj.menuguru_customer.infra.controllers.dtos.CreateCustomerResponseDTO;
import com.perisatto.fiapprj.menuguru_customer.infra.controllers.dtos.GetCustomerResponseDTO;

import io.cucumber.java.DataTableType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;

public class StepDefinition {
	
	private Response response;
	private List<CreateCustomerRequestDTO> createCustomerRequests = new ArrayList<>();
	private CreateCustomerResponseDTO createCustomerResponse;
	private String newEmail;
	private final String ENDPOINT_CUSTOMER_API = "http://localhost:8080/menuguru-customers/v1/customers";
	
    @DataTableType
    public CreateCustomerRequestDTO customerEntry(Map<String, String> entry) {
    	CreateCustomerRequestDTO customerEntry = new CreateCustomerRequestDTO();
    	customerEntry.setDocumentNumber(entry.get("documentNumber"));
    	customerEntry.setName(entry.get("name"));
    	customerEntry.setEmail(entry.get("email"));
        return customerEntry;
    }
	
	
	@Given("customer has the following attribuites")
	public void customer_has_the_following_attribuites(List<CreateCustomerRequestDTO> customerDataTable) {
		createCustomerRequests = customerDataTable;
	}
	
	
	@When("register a new customer")
	public CreateCustomerResponseDTO register_a_new_customer() {
		var createCustomerRequest = createCustomerRequests.get(0);
		
		response = given()
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.body(createCustomerRequest)
				.when()
				.post(ENDPOINT_CUSTOMER_API);
		return response.then().extract().as(CreateCustomerResponseDTO.class);
	}
	
	@Then("the customer is successfully registered")
	public void the_customer_is_successfully_registered() {
	    response.then()
	    .statusCode(HttpStatus.CREATED.value());
	}

	@Then("should be showed")
	public void should_be_showed() {
		response.then()
		.body(matchesJsonSchemaInClasspath("./schemas/CreateCustomerResponse.json"));
	}
	
	@Given("the customer is already registered with the following attribuites")
	public void the_following_customer_is_already_registered(List<CreateCustomerRequestDTO> customerDataTable) {
		createCustomerRequests = customerDataTable;
		createCustomerResponse = register_a_new_customer();
	}

	@When("ask for customer information")
	public void ask_for_customer_information() {
	    response = given()
	    		.contentType(MediaType.APPLICATION_JSON_VALUE)
	    		.when()
	    		.get(ENDPOINT_CUSTOMER_API + "/" + createCustomerResponse.getId().toString());
	}

	@Then("the customer information is retrieved")
	public void the_customer_information_is_retrieved() {
	    response.then()
	    .statusCode(HttpStatus.OK.value())
	    .body(matchesJsonSchemaInClasspath("./schemas/CreateCustomerResponse.json"));
	}	
	
	@When("gives a new email")
	public GetCustomerResponseDTO gives_a_new_email() {
		newEmail = "fernanda.machado@anothermail.com";
		
		var updateCustomerRequest = createCustomerRequests.get(0);
		
		updateCustomerRequest.setEmail(newEmail);
		
		response = given()
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.body(updateCustomerRequest)
				.when()
				.patch(ENDPOINT_CUSTOMER_API + "/" + createCustomerResponse.getId().toString());
		return response.then().extract().as(GetCustomerResponseDTO.class);
	}

	@Then("updates the customer information with new email")
	public void updates_the_customer_information_with_new_email() {
	    response.then()
	    .statusCode(HttpStatus.OK.value())
	    .body(matchesJsonSchemaInClasspath("./schemas/CreateCustomerResponse.json"))
	    .body("email", equalTo(newEmail));
	}
}