Feature: User management

  Scenario: Register user
   Given user has the following attribuites
   	| documentNumber | name            | email                        |
   	| 35732996010    | Roberto Machado | roberto.machado@bestmail.com |
    When register a new user
    Then the user is successfully registered
    And should be showed

	Scenario: Retrieve user information
	 Given the user is already registered with the following attribuites
   	| documentNumber | name            | email                        |
   	| 42266418092    | Juliana Machado | juliana.machado@bestmail.com |
    When ask for user information
    Then the user information is retrieved
    
  Scenario: Update user information
	 Given the user is already registered with the following attribuites
   	| documentNumber | name            | email                        |
   	| 52562451040    | Fernanda Machado | fernanda.machado@bestmail.com |
   	When gives a new email
   	Then updates the user information with new email