package utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.time.Duration;
import java.util.HashMap;

public class DriverManager {
  private static WebDriver driver;

  public static WebDriver getDriver() {
    if (driver == null) {
      String browser = ConfigManager.getProperty("browser").toLowerCase();
      boolean headless = ConfigManager.getBooleanProperty("headless");

      switch (browser) {
        case "chrome" :
          WebDriverManager.chromedriver().setup();
          ChromeOptions options = new ChromeOptions();
          options.addArguments("--remote-allow-origins=*");
          options.addArguments("--ignore-certificate-errors");
          options.setCapability("goog:loggingPrefs", new HashMap<String, String>() {
            {
              put("browser", "ALL");
            }
          });
          if (headless) {
            options.addArguments("--headless");
          }
          driver = new ChromeDriver(options);
          driver.manage().window().maximize();
          driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
          break;
        case "firefox" :
          WebDriverManager.firefoxdriver().setup();
          FirefoxOptions firefoxOptions = new FirefoxOptions();
          if (headless) {
            firefoxOptions.addArguments("--headless");
          }
          driver = new FirefoxDriver(firefoxOptions);
          break;
        default :
          throw new IllegalArgumentException("Unsupported browser: " + browser);
      }
    }
    return driver;
  }

  public static void quitDriver() {
    if (driver != null) {
      driver.quit();
      driver = null;
    }
  }
}
