package utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.time.Duration;
import java.util.HashMap;
import java.util.concurrent.locks.ReentrantLock;

public class DriverManager {
  private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();
  private static final ReentrantLock driverLock = new ReentrantLock();

  public static WebDriver getDriver() {
    if (driverThreadLocal.get() == null) {
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
          driverThreadLocal.set(new ChromeDriver(options));
          driverThreadLocal.get().manage().window().maximize();
          driverThreadLocal.get().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
          break;
        case "firefox" :
          WebDriverManager.firefoxdriver().setup();
          FirefoxOptions firefoxOptions = new FirefoxOptions();
          if (headless) {
            firefoxOptions.addArguments("--headless");
          }
          driverThreadLocal.set(new FirefoxDriver(firefoxOptions));
          break;
        default :
          throw new IllegalArgumentException("Unsupported browser: " + browser);
      }
    }
    return driverThreadLocal.get();
  }

  public static void quitDriver() {
    WebDriver driver = driverThreadLocal.get();
    if (driver != null) {
      try {
        driver.quit();
      } catch (Exception e) {
        LoggerUtils.warn("Error quitting driver: " + e.getMessage());
      } finally {
        driverThreadLocal.remove();
      }
    }
  }

  /**
   * Safely takes a screenshot using the current driver instance
   *
   * @param name
   *          Name to use for the screenshot file
   * @return true if the screenshot was successfully taken, false otherwise
   */
  public static boolean safeScreenshot(String name) {
    driverLock.lock();
    try {
      WebDriver currentDriver = driverThreadLocal.get();
      if (currentDriver == null) {
        LoggerUtils.warn("Cannot take screenshot - no active WebDriver");
        return false;
      }

      // Check if driver is still valid before taking screenshot
      try {
        currentDriver.getCurrentUrl();
        ReportManager.addScreenshot(currentDriver, name);
        return true;
      } catch (Exception e) {
        LoggerUtils.warn("Cannot take screenshot - WebDriver session is invalid: " + e.getMessage());
        return false;
      }
    } finally {
      driverLock.unlock();
    }
  }
}
