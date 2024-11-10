Feature: Customer management

  Scenario: Register customer
   Given customer has the following attribuites
   	| documentNumber | name            | email                        |
   	| 35732996010    | Roberto Machado | roberto.machado@bestmail.com |
    When register a new customer
    Then the customer is successfully registered
    And should be showed

	Scenario: Retrieve customer information
	 Given the customer is already registered with the following attribuites
   	| documentNumber | name            | email                        |
   	| 42266418092    | Juliana Machado | juliana.machado@bestmail.com |
    When ask for customer information
    Then the customer information is retrieved
    
  Scenario: Update customer information
	 Given the customer is already registered with the following attribuites
   	| documentNumber | name            | email                        |
   	| 52562451040    | Fernanda Machado | fernanda.machado@bestmail.com |
   	When gives a new email
   	Then updates the customer information with new email