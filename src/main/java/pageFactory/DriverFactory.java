package pageFactory;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
import java.time.Duration;

public class DriverFactory {

  public static WebDriver getDriver(String browserName) {
    WebDriver driver;

    switch (browserName.toLowerCase()) {
      case "chrome" -> {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--disable-extensions", "--disable-gpu", "--no-sandbox");
        driver = new ChromeDriver(chromeOptions);
      }
      case "firefox" -> driver = new FirefoxDriver();
      case "safari" -> driver = new SafariDriver();
      default -> throw new IllegalArgumentException("Unsupported browser: " + browserName);
    }

    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    driver.manage().window().maximize();
    return driver;
  }
}