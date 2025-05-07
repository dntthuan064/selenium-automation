@web-form
Feature: Web Form Interactions
  As a user
  I want to interact with web forms
  So that I can submit data and verify responses

  @smoke
  Scenario: Submit a web form with valid data
    Given I am on the web form page
    # When I enter "John" in the first name field
    # And I enter "Doe" in the last name field
    # And I enter "john.doe@example.com" in the email field
    # And I click the submit button
    # Then I should see a success message

  @regression
  Scenario: Submit a web form with invalid data
    Given I am on the web form page
    # When I enter "" in the first name field
    # And I enter "" in the last name field
    # And I enter "invalid-email" in the email field
    # And I click the submit button
    # Then I should see validation error messages 