package com.perisatto.fiapprj.menuguru_customer.bdd;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import com.perisatto.fiapprj.menuguru_customer.infra.controllers.dtos.CreateCustomerRequestDTO;
import com.perisatto.fiapprj.menuguru_customer.infra.controllers.dtos.CreateCustomerResponseDTO;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;

public class StepDefinition {
	
	private Response response;
	private CreateCustomerResponseDTO createCustomerResponse;
	private final String ENDPOINT_CUSTOMER_API = "http://localhost:8080/menuguru-customers/v1/customers";
	
	@When("register a new customer")
	public CreateCustomerResponseDTO register_a_new_customer() {
		var createCustomerRequest = new CreateCustomerRequestDTO();
		createCustomerRequest.setName("Roberto Machado");
		createCustomerRequest.setEmail("roberto.machado@bestmail.com");
		createCustomerRequest.setDocumentNumber("35732996010");
		
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
	
	@Given("customer already registered")
	public void customer_already_registered() {
		createCustomerResponse = register_a_new_customer();
	}

	@When("ask for customer information")
	public void ask_for_customer_information() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}

	@Then("retrieve customer information")
	public void retrieve_customer_information() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}
	
	@When("deletes a customer")
	public void deletes_a_customer() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}

	@Then("the customer is successfully deleted")
	public void the_customer_is_successfully_deleted() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}
}
