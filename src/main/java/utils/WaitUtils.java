package utils;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import java.time.Duration;

public class WaitUtils {

  private static final int DEFAULT_TIMEOUT = 30;
  private static final int WAIT_DURATION = ConfigManager.getIntProperty("explicit.wait");

  public static WebElement waitForVisibility(WebDriver driver, WebElement element) {
    return new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_TIMEOUT))
      .until(ExpectedConditions.visibilityOf(element));
  }

  public static WebElement waitForClickable(WebDriver driver, WebElement element) {
    return new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_TIMEOUT))
      .until(ExpectedConditions.elementToBeClickable(element));
  }

  public static WebElement fluentWait(WebDriver driver, By locator, int timeoutSec, int pollingSec) {
    Wait<WebDriver> wait = new FluentWait<>(driver).withTimeout(Duration.ofSeconds(timeoutSec))
      .pollingEvery(Duration.ofSeconds(pollingSec)).ignoring(NoSuchElementException.class)
      .ignoring(StaleElementReferenceException.class);

    return wait.until(webDriver -> webDriver.findElement(locator));
  }

  public static void waitForElementVisible(WebDriver driver, WebElement element) {
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(WAIT_DURATION));
    wait.until(ExpectedConditions.visibilityOf(element));
  }

  public static void waitForElementClickable(WebDriver driver, WebElement element) {
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(WAIT_DURATION));
    wait.until(ExpectedConditions.elementToBeClickable(element));
  }

  public static void waitForElementInvisible(WebDriver driver, WebElement element) {
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(WAIT_DURATION));
    wait.until(ExpectedConditions.invisibilityOf(element));
  }

  public static void waitForTextPresent(WebDriver driver, WebElement element, String text) {
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(WAIT_DURATION));
    wait.until(ExpectedConditions.textToBePresentInElement(element, text));
  }

  public static void waitForUrlContains(WebDriver driver, String text) {
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(WAIT_DURATION));
    wait.until(ExpectedConditions.urlContains(text));
  }
}
