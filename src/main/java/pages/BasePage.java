package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import utils.WaitUtils;
import utils.ScreenshotUtils;
import utils.LoggerUtils;

public abstract class BasePage {
  protected final WebDriver driver;
  protected final WebDriverWait wait;
  private static final Duration WAIT_TIMEOUT = Duration.ofSeconds(10);

  protected BasePage(WebDriver driver) {
    this.driver = driver;
    this.wait = new WebDriverWait(driver, WAIT_TIMEOUT);
    PageFactory.initElements(driver, this);
    LoggerUtils.info("Initialized " + this.getClass().getSimpleName());
  }

  protected void waitForElement(WebElement element) {
    wait.until(ExpectedConditions.visibilityOf(element));
  }

  protected void click(WebElement element) {
    LoggerUtils.debug("Clicking element: " + element);
    WaitUtils.waitForElementClickable(driver, element);
    element.click();
  }

  protected void sendKeys(WebElement element, String text) {
    LoggerUtils.debug("Sending keys to element: " + element + " with text: " + text);
    WaitUtils.waitForElementVisible(driver, element);
    element.clear();
    element.sendKeys(text);
  }

  protected String getText(WebElement element) {
    LoggerUtils.debug("Getting text from element: " + element);
    WaitUtils.waitForElementVisible(driver, element);
    return element.getText();
  }

  protected boolean isElementDisplayed(WebElement element) {
    try {
      WaitUtils.waitForElementVisible(driver, element);
      return element.isDisplayed();
    } catch (NoSuchElementException | StaleElementReferenceException e) {
      LoggerUtils.error("Element not displayed: " + element, e);
      return false;
    }
  }

  public void takeScreenshot(String testName) {
    LoggerUtils.info("Taking screenshot for test: " + testName);
    ScreenshotUtils.takeScreenshot(driver, testName);
  }
}
