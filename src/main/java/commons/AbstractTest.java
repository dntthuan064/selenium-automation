package commons;

import java.time.Duration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.Reporter;
import io.github.bonigarcia.wdm.WebDriverManager;

public class AbstractTest {
  private WebDriver driver;

  protected final Log log;

  protected AbstractTest() {
    log = LogFactory.getLog(getClass());
  }

  protected WebDriver getBrowserDriver(String browserName) {

    if (browserName.equals("chrome_ui")) {
      WebDriverManager.chromedriver().setup();
      driver = new ChromeDriver();
    } else if (browserName.equals("chrome_headless")) {
      WebDriverManager.chromedriver().setup();
      ChromeOptions option = new ChromeOptions();
      option.addArguments("headless");
      option.addArguments("window-size=1920x1080");
      driver = new ChromeDriver(option);
    } else {
      throw new RuntimeException("Please input valid browser name value!");
    }
    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10)

    );
    driver.manage().window().maximize();
    return driver;
  }

  private boolean checkTrue(boolean condition) {
    boolean pass = true;
    try {
      if (condition == true) {
        log.info(" -------------------------- PASSED -------------------------- ");
      } else {
        log.info(" -------------------------- FAILED -------------------------- ");
      }
      Assert.assertTrue(condition);
    } catch (Throwable e) {
      pass = false;

      // Add lỗi vào ReportNG
      VerificationFailures.getFailures().addFailureForTest(Reporter.getCurrentTestResult(), e);
      Reporter.getCurrentTestResult().setThrowable(e);
    }
    return pass;
  }

  protected boolean verifyTrue(boolean condition) {
    return checkTrue(condition);
  }

  private boolean checkFailed(boolean condition) {
    boolean pass = true;
    try {
      if (condition == false) {
        log.info(" -------------------------- PASSED -------------------------- ");
      } else {
        log.info(" -------------------------- FAILED -------------------------- ");
      }
      Assert.assertFalse(condition);
    } catch (Throwable e) {
      pass = false;
      VerificationFailures.getFailures().addFailureForTest(Reporter.getCurrentTestResult(), e);
      Reporter.getCurrentTestResult().setThrowable(e);
    }
    return pass;
  }

  protected boolean verifyFalse(boolean condition) {
    return checkFailed(condition);
  }

  private boolean checkEquals(Object actual, Object expected) {
    boolean pass = true;
    try {
      Assert.assertEquals(actual, expected);
      log.info(" -------------------------- PASSED -------------------------- ");
    } catch (Throwable e) {
      pass = false;
      log.info(" -------------------------- FAILED -------------------------- ");
      VerificationFailures.getFailures().addFailureForTest(Reporter.getCurrentTestResult(), e);
      Reporter.getCurrentTestResult().setThrowable(e);
    }
    return pass;
  }

  protected boolean verifyEquals(Object actual, Object expected) {
    return checkEquals(actual, expected);
  }

  public WebDriver getDriver() {
    return driver;
  }
}
