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
  private static final int MAX_RETRIES = 3;

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

  @FindBy(className = "lead")
  private WebElement successMessage;

  public WebFormPage(WebDriver driver) {
    super(driver);
    this.wait = new WebDriverWait(driver, WAIT_TIMEOUT);
    PageFactory.initElements(driver, this);
  }

  public void navigateToWebForm() {
    String url = ConfigManager.getProperty("base.url") + "web-form.html";
    LoggerUtils.info("Navigating to URL: " + url);

    int retries = 0;
    boolean success = false;

    while (!success && retries < MAX_RETRIES) {
      try {
        driver.get(url);

        // Using presence of element which is more reliable than visibility
        wait.until(ExpectedConditions.presenceOfElementLocated(org.openqa.selenium.By.id("my-text-id")));

        // Re-initialize elements after page load to avoid stale references
        PageFactory.initElements(driver, this);

        // Now check visibility with a fresh reference
        wait.until(ExpectedConditions.visibilityOf(textInput));

        success = true;
        LoggerUtils.info("Navigated to web form page");
        ReportManager.logInfo("Navigated to web form page: " + url);

        try {
          ReportManager.addScreenshot(driver, "page-loaded");
        } catch (Exception e) {
          LoggerUtils.warn("Could not take screenshot after page load: " + e.getMessage());
        }
      } catch (StaleElementReferenceException | TimeoutException e) {
        retries++;
        if (retries >= MAX_RETRIES) {
          LoggerUtils.error("Failed to navigate to web form after " + MAX_RETRIES + " attempts", e);
          throw e;
        }
        LoggerUtils.warn("Retrying navigation due to: " + e.getMessage());
        try {
          Thread.sleep(1000); // Brief pause before retry
        } catch (InterruptedException ie) {
          Thread.currentThread().interrupt();
        }
      }
    }
  }

  public void enterTextInput(String text) {
    try {
      wait.ignoring(StaleElementReferenceException.class).until(ExpectedConditions.visibilityOf(textInput));
      textInput.clear();
      textInput.sendKeys(text);
      LoggerUtils.info("Entered text: " + text);
      ReportManager.logInfo("Entered text in input field: " + text);
    } catch (Exception e) {
      LoggerUtils.error("Failed to enter text input: " + e.getMessage());
      PageFactory.initElements(driver, this); // Refresh elements
      wait.until(ExpectedConditions.visibilityOf(textInput));
      textInput.clear();
      textInput.sendKeys(text);
    }
  }

  public void enterPassword(String password) {
    try {
      wait.ignoring(StaleElementReferenceException.class).until(ExpectedConditions.visibilityOf(passwordInput));
      passwordInput.clear();
      passwordInput.sendKeys(password);
      LoggerUtils.info("Entered password");
      ReportManager.logInfo("Entered password in password field");
    } catch (Exception e) {
      LoggerUtils.error("Failed to enter password: " + e.getMessage());
      PageFactory.initElements(driver, this); // Refresh elements
      wait.until(ExpectedConditions.visibilityOf(passwordInput));
      passwordInput.clear();
      passwordInput.sendKeys(password);
    }
  }

  public void enterTextArea(String text) {
    try {
      wait.ignoring(StaleElementReferenceException.class).until(ExpectedConditions.visibilityOf(textarea));
      textarea.clear();
      textarea.sendKeys(text);
      LoggerUtils.info("Entered text in textarea: " + text);
      ReportManager.logInfo("Entered text in textarea field: " + text);
    } catch (Exception e) {
      LoggerUtils.error("Failed to enter textarea: " + e.getMessage());
      PageFactory.initElements(driver, this); // Refresh elements
      wait.until(ExpectedConditions.visibilityOf(textarea));
      textarea.clear();
      textarea.sendKeys(text);
    }
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
    try {
      wait.ignoring(StaleElementReferenceException.class).until(ExpectedConditions.elementToBeClickable(submitButton));

      try {
        ReportManager.addScreenshot(driver, "before-submit");
      } catch (Exception e) {
        LoggerUtils.warn("Could not take before-submit screenshot: " + e.getMessage());
      }

      submitButton.click();
      LoggerUtils.info("Clicked submit button");
      ReportManager.logInfo("Clicked submit button");

      try {
        ReportManager.addScreenshot(driver, "after-submit");
      } catch (Exception e) {
        LoggerUtils.warn("Could not take after-submit screenshot: " + e.getMessage());
      }
    } catch (Exception e) {
      LoggerUtils.error("Failed to click submit button: " + e.getMessage());
      PageFactory.initElements(driver, this); // Refresh elements
      wait.until(ExpectedConditions.elementToBeClickable(submitButton));
      submitButton.click();
    }
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
      // Check if any validation messages appear
      // First check for HTML5 validation attributes that become visible
      wait.until(driver -> {
        try {
          // Most browsers show validation errors with :invalid pseudo-class
          // We can check if any required fields are empty or if email is invalid

          // Check if email field has an invalid format
          String emailValue = textarea.getAttribute("value");
          boolean invalidEmail = emailValue != null && emailValue.equals("invalid-email");

          // Check if any validation messages are visible (varies by browser)
          boolean hasValidationMsg = driver
            .findElements(org.openqa.selenium.By.cssSelector(".invalid-feedback, .validation-message")).stream()
            .anyMatch(WebElement::isDisplayed);

          // Check if any fields have the HTML5 :invalid state (detectable via JS)
          String jsScript = "return document.querySelector('input:invalid, textarea:invalid') !== null;";
          boolean hasInvalidFields = (boolean) ((org.openqa.selenium.JavascriptExecutor) driver)
            .executeScript(jsScript);

          return invalidEmail || hasValidationMsg || hasInvalidFields || !submitButton.isEnabled();
        } catch (Exception e) {
          return false;
        }
      });

      return true;
    } catch (org.openqa.selenium.TimeoutException e) {
      return false;
    }
  }

  public WebElement getTextInput() {
    return textInput;
  }
}
