package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.StaleElementReferenceException;
import java.time.Duration;
import utils.ConfigManager;
import utils.LoggerUtils;
import utils.ReportManager;

public class WebFormPage extends BasePage {
  private final WebDriverWait wait;
  private static final Duration WAIT_TIMEOUT = Duration.ofSeconds(10);

  @FindBy(id = "my-text-id")
  private WebElement textInput;

  @FindBy(name = "my-password")
  private WebElement passwordInput;

  @FindBy(name = "my-textarea")
  private WebElement textarea;

  @FindBy(name = "my-select")
  private WebElement selectDropdown;

  @FindBy(name = "my-datalist")
  private WebElement datalist;

  @FindBy(name = "my-file")
  private WebElement fileInput;

  @FindBy(name = "my-colors")
  private WebElement colorPicker;

  @FindBy(name = "my-date")
  private WebElement datePicker;

  @FindBy(name = "my-range")
  private WebElement rangeSlider;

  @FindBy(css = "button[type='submit']")
  private WebElement submitButton;

  @FindBy(id = "message")
  private WebElement successMessage;

  public WebFormPage(WebDriver driver) {
    super(driver);
    this.wait = new WebDriverWait(driver, WAIT_TIMEOUT);
    PageFactory.initElements(driver, this);
  }

  public void navigateToWebForm() {
    String url = ConfigManager.getProperty("base.url") + "web-form.html";
    LoggerUtils.info("Navigating to URL: " + url);
    driver.get(url);
    wait.until(ExpectedConditions.visibilityOf(textInput));
    LoggerUtils.info("Navigated to web form page");
    ReportManager.logInfo("Navigated to web form page: " + url);
    ReportManager.addScreenshot(driver, "page-loaded");
  }

  public void enterTextInput(String text) {
    wait.until(ExpectedConditions.visibilityOf(textInput));
    textInput.clear();
    textInput.sendKeys(text);
    LoggerUtils.info("Entered text: " + text);
    ReportManager.logInfo("Entered text in input field: " + text);
  }

  public void enterPassword(String password) {
    wait.until(ExpectedConditions.visibilityOf(passwordInput));
    passwordInput.clear();
    passwordInput.sendKeys(password);
    LoggerUtils.info("Entered password");
    ReportManager.logInfo("Entered password in password field");
  }

  public void enterTextArea(String text) {
    wait.until(ExpectedConditions.visibilityOf(textarea));
    textarea.clear();
    textarea.sendKeys(text);
    LoggerUtils.info("Entered text in textarea: " + text);
    ReportManager.logInfo("Entered text in textarea field: " + text);
  }

  public void selectOption(String option) {
    click(selectDropdown);
  }

  public void uploadFile(String filePath) {
    fileInput.sendKeys(filePath);
  }

  public void setColor(String color) {
    sendKeys(colorPicker, color);
  }

  public void setDate(String date) {
    sendKeys(datePicker, date);
  }

  public void setRange(int value) {
    // Add range setting logic
  }

  public void clickSubmitButton() {
    wait.until(ExpectedConditions.elementToBeClickable(submitButton));
    ReportManager.addScreenshot(driver, "before-submit");
    submitButton.click();
    LoggerUtils.info("Clicked submit button");
    ReportManager.logInfo("Clicked submit button");
    ReportManager.addScreenshot(driver, "after-submit");
  }

  public void verifySuccessMessage() {
    wait.until(ExpectedConditions.visibilityOf(successMessage));
    String message = successMessage.getText();
    LoggerUtils.info("Success message: " + message);
    ReportManager.logInfo("Verified success message: " + message);
    ReportManager.addScreenshot(driver, "success-message");
  }

  public boolean isFormSubmitted() {
    try {
      // Wait for the form to be reset or for a success indicator
      WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(3));

      // Check if the form fields are reset
      boolean isTextInputEmpty = shortWait.until(ExpectedConditions.visibilityOf(textInput)).getAttribute("value")
          .isEmpty();

      boolean isPasswordEmpty = shortWait.until(ExpectedConditions.visibilityOf(passwordInput)).getAttribute("value")
          .isEmpty();

      boolean isTextareaEmpty = shortWait.until(ExpectedConditions.visibilityOf(textarea)).getAttribute("value")
          .isEmpty();

      return isTextInputEmpty && isPasswordEmpty && isTextareaEmpty;
    } catch (TimeoutException | StaleElementReferenceException e) {
      return false;
    }
  }

  public boolean hasValidationErrors() {
    try {
      // Check if the textarea still contains the invalid email
      String value = wait.until(ExpectedConditions.visibilityOf(textarea)).getAttribute("value");
      return "invalid-email".equals(value);
    } catch (Exception e) {
      return false;
    }
  }
}
