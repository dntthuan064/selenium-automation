Feature: Web Form Tests
  As a user
  I want to interact with a web form
  So that I can test form submission functionality

  Scenario: Submit form with valid data
    Given I am on the web form page
    When I enter "John Doe" in the text input field
    And I enter "password123" in the password field
    And I enter "This is a test message" in the textarea field
    And I click the submit button
    Then I should see a success message 