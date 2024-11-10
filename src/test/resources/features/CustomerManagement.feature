Feature: Customer management

  Scenario: Register customer
    When register a new customer
    Then the customer is successfully registered
    And should be showed
    
#  Scenario: Retrieve customer information
#		Given customer already registered
#		 When ask for customer information
#		 Then retrieve customer information
#		 
#	Scenario: Delete customer
#		Given customer already registered
#		 When deletes a customer
#		 Then the customer is successfully deleted 
