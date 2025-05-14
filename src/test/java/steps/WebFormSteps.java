package steps;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.WebFormPage;
import utils.DriverManager;
import utils.LoggerUtils;
import utils.ReportManager;
import utils.TestDataManager;

public class WebFormSteps {
  private WebDriver driver;
  private WebFormPage webFormPage;
  private WebDriverWait wait;

  @Before
  public void setup() {
    LoggerUtils.info("Setting up test environment");
    driver = DriverManager.getDriver();
    webFormPage = new WebFormPage(driver);
    wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    ReportManager.startTest("Web Form Test");
  }

  @After
  public void tearDown() {
    LoggerUtils.info("Tearing down test environment");

    try {
      if (driver != null) {
        DriverManager.safeScreenshot("final-state");
      }
    } catch (Exception e) {
      LoggerUtils.error("Failed to capture final screenshot", e);
    }

    try {
      ReportManager.endTest();
    } catch (Exception e) {
      LoggerUtils.error("Failed to end test report", e);
    }

    try {
      DriverManager.quitDriver();
      LoggerUtils.info("WebDriver quit successfully");
    } catch (Exception e) {
      LoggerUtils.error("Error during driver cleanup", e);
    }

    driver = null;
  }

  @Given("I am on the web form page")
  public void iAmOnTheWebFormPage() {
    webFormPage.navigateToWebForm();
  }

  @When("I enter {string} in the text input field")
  public void iEnterInTheTextInputField(String text) {
    webFormPage.enterTextInput(text);
  }

  @When("I enter {string} in the password field")
  public void iEnterInThePasswordField(String password) {
    webFormPage.enterPassword(password);
  }

  @When("I enter {string} in the textarea field")
  public void iEnterInTheTextareaField(String text) {
    webFormPage.enterTextArea(text);
  }

  @When("I click the submit button")
  public void iClickTheSubmitButton() {
    webFormPage.clickSubmitButton();
  }

  @Then("I should see a success message")
  public void iShouldSeeASuccessMessage() {
    webFormPage.verifySuccessMessage();
  }

  @When("I enter {string} in the first name field")
  public void enterFirstName(String firstName) {
    WebElement firstNameField = wait
      .until(ExpectedConditions.visibilityOfElementLocated(org.openqa.selenium.By.id("my-text-id")));
    firstNameField.clear();
    firstNameField.sendKeys(firstName);
    LoggerUtils.info("Entered first name: " + firstName);
    ReportManager.logInfo("Entered first name: " + firstName);
  }

  @When("I enter {string} in the last name field")
  public void enterLastName(String lastName) {
    WebElement lastNameField = wait
      .until(ExpectedConditions.visibilityOfElementLocated(org.openqa.selenium.By.name("my-password")));
    lastNameField.clear();
    lastNameField.sendKeys(lastName);
    LoggerUtils.info("Entered last name: " + lastName);
  }

  @When("I enter {string} in the email field")
  public void enterEmail(String email) {
    WebElement emailField = wait
      .until(ExpectedConditions.visibilityOfElementLocated(org.openqa.selenium.By.name("my-textarea")));
    emailField.clear();
    emailField.sendKeys(email);
    LoggerUtils.info("Entered email: " + email);
  }

  @When("I fill in the text input with {string}")
  public void iFillInTheTextInputWith(String text) {
    LoggerUtils.info("Filling text input");
    webFormPage.enterTextInput(TestDataManager.getTestData("webForm", "textInput"));
  }

  @When("I fill in the password with {string}")
  public void iFillInThePasswordWith(String password) {
    LoggerUtils.info("Filling password");
    webFormPage.enterPassword(TestDataManager.getTestData("webForm", "password"));
  }

  @When("I fill in the textarea with {string}")
  public void iFillInTheTextareaWith(String text) {
    LoggerUtils.info("Filling textarea");
    webFormPage.enterTextArea(TestDataManager.getTestData("webForm", "textarea"));
  }

  @When("I select {string} from the dropdown")
  public void iSelectFromTheDropdown(String option) {
    LoggerUtils.info("Selecting dropdown option");
    webFormPage.selectOption(TestDataManager.getTestData("webForm", "option"));
  }

  @When("I upload a file")
  public void iUploadAFile() {
    LoggerUtils.info("Uploading file");
    String filePath = System.getProperty("user.dir") + "/src/test/resources/test-file.txt";
    webFormPage.uploadFile(filePath);
  }

  @When("I set the color to {string}")
  public void iSetTheColorTo(String color) {
    LoggerUtils.info("Setting color");
    webFormPage.setColor(TestDataManager.getTestData("webForm", "color"));
  }

  @When("I set the date to {string}")
  public void iSetTheDateTo(String date) {
    LoggerUtils.info("Setting date");
    webFormPage.setDate(TestDataManager.getTestData("webForm", "date"));
  }

  @When("I set the range to {int}")
  public void iSetTheRangeTo(int value) {
    LoggerUtils.info("Setting range");
    webFormPage.setRange(TestDataManager.getTestDataInt("webForm", "range"));
  }

  @Then("I should see validation error messages")
  public void verifyValidationErrors() {
    try {
      boolean hasErrors = webFormPage.hasValidationErrors();
      Assert.assertTrue(hasErrors, "Form should show validation errors");
      LoggerUtils.info("Validation errors verified successfully");
    } catch (Exception e) {
      LoggerUtils.error("Failed to verify validation errors", e);
      throw e;
    }
  }
}
