package com.perisatto.fiapprj.user_management.bdd;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.CoreMatchers.equalTo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import com.perisatto.fiapprj.user_management.application.interfaces.IAMUserManagement;
import com.perisatto.fiapprj.user_management.infra.controllers.dtos.CreateUserRequestDTO;
import com.perisatto.fiapprj.user_management.infra.controllers.dtos.CreateUserResponseDTO;
import com.perisatto.fiapprj.user_management.infra.controllers.dtos.GetUserResponseDTO;

import io.cucumber.java.DataTableType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;

public class StepDefinition {
	
	private Response response;
	private List<CreateUserRequestDTO> createUserRequests = new ArrayList<>();
	private CreateUserResponseDTO createUserResponse;
	private String newEmail;
	private final String ENDPOINT_CUSTOMER_API = "http://localhost:8080/user-management/v1/users";
	
    @DataTableType
    public CreateUserRequestDTO userEntry(Map<String, String> entry) {
    	CreateUserRequestDTO userEntry = new CreateUserRequestDTO();
    	userEntry.setDocumentNumber(entry.get("documentNumber"));
    	userEntry.setName(entry.get("name"));
    	userEntry.setEmail(entry.get("email"));
        return userEntry;
    }
	
	
	@Given("user has the following attribuites")
	public void user_has_the_following_attribuites(List<CreateUserRequestDTO> userDataTable) {
		createUserRequests = userDataTable;
	}
	
	
	@When("register a new user")
	public CreateUserResponseDTO register_a_new_user() {
		var createUserRequest = createUserRequests.get(0);
		
		response = given()
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.body(createUserRequest)
				.when()
				.post(ENDPOINT_CUSTOMER_API);
		return response.then().extract().as(CreateUserResponseDTO.class);
	}
	
	@Then("the user is successfully registered")
	public void the_user_is_successfully_registered() {
	    response.then()
	    .statusCode(HttpStatus.CREATED.value());
	}

	@Then("should be showed")
	public void should_be_showed() {
		response.then()
		.body(matchesJsonSchemaInClasspath("./schemas/CreateUserResponse.json"));
	}
	
	@Given("the user is already registered with the following attribuites")
	public void the_following_user_is_already_registered(List<CreateUserRequestDTO> userDataTable) {
		createUserRequests = userDataTable;
		createUserResponse = register_a_new_user();
	}

	@When("ask for user information")
	public void ask_for_user_information() {
	    response = given()
	    		.contentType(MediaType.APPLICATION_JSON_VALUE)
	    		.when()
	    		.get(ENDPOINT_CUSTOMER_API + "/{userId}", createUserResponse.getId().toString());
	}

	@Then("the user information is retrieved")
	public void the_user_information_is_retrieved() {
	    response.then()
	    .statusCode(HttpStatus.OK.value())
	    .body(matchesJsonSchemaInClasspath("./schemas/CreateUserResponse.json"));
	}	
	
	@When("gives a new email")
	public GetUserResponseDTO gives_a_new_email() {
		newEmail = "fernanda.machado@anothermail.com";
		
		var updateUserRequest = createUserRequests.get(0);
		
		updateUserRequest.setEmail(newEmail);
		
		response = given()
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.body(updateUserRequest)
				.when()
				.patch(ENDPOINT_CUSTOMER_API + "/{userId}", createUserResponse.getId().toString());
		return response.then().extract().as(GetUserResponseDTO.class);
	}

	@Then("updates the user information with new email")
	public void updates_the_user_information_with_new_email() {
	    response.then()
	    .statusCode(HttpStatus.OK.value())
	    .body(matchesJsonSchemaInClasspath("./schemas/CreateUserResponse.json"))
	    .body("email", equalTo(newEmail));
	}
}