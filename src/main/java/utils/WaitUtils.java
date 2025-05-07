package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class WaitUtils {
  private static final int WAIT_DURATION = ConfigManager.getIntProperty("explicit.wait");

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
