package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * ReportManager provides additional reporting capability to supplement the
 * ExtentCucumberAdapter. This allows for custom screenshots and logging at
 * specific points in the test.
 */
public class ReportManager {
  private static final ExtentReports extentReports = new ExtentReports();
  private static final ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();
  private static final String REPORT_DIR = ConfigManager.getProperty("report.path") != null
    && !ConfigManager.getProperty("report.path").isEmpty() ? ConfigManager.getProperty("report.path") : "test-reports";
  private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
  private static final String RUN_TIMESTAMP = LocalDateTime.now().format(DATE_FORMATTER);
  private static final String SCREENSHOTS_FOLDER = String.format("%s/%s/screenshots", REPORT_DIR, RUN_TIMESTAMP);

  static {
    setupExtentReports();
  }

  private static void setupExtentReports() {
    String reportPath = String.format("%s/%s/manual-reports", REPORT_DIR, RUN_TIMESTAMP);

    // Create base reports directory first
    createDirectories(REPORT_DIR);

    // Create directories if they don't exist
    createDirectories(reportPath);
    createDirectories(SCREENSHOTS_FOLDER);

    ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath + "/manual-report.html");
    sparkReporter.config().setReportName("Manual Screenshots and Logs");
    sparkReporter.config().setDocumentTitle("Manual Test Report");

    extentReports.attachReporter(sparkReporter);
    extentReports.setSystemInfo("OS", System.getProperty("os.name"));
    extentReports.setSystemInfo("Java Version", System.getProperty("java.version"));
  }

  private static void createDirectories(String path) {
    try {
      Files.createDirectories(Paths.get(path));
    } catch (IOException e) {
      LoggerUtils.error("Failed to create directory: " + path, e);
    }
  }

  public static void startTest(String testName) {
    ExtentTest test = extentReports.createTest(testName);
    extentTest.set(test);
  }

  public static void endTest() {
    extentReports.flush();
  }

  public static void addScreenshot(WebDriver driver, String name) {
    if (driver == null || extentTest.get() == null) {
      LoggerUtils.warn("Cannot capture screenshot - driver or test report is null");
      return;
    }

    try {
      // Check if driver is still valid
      if (!isDriverValid(driver)) {
        LoggerUtils.warn("Cannot capture screenshot - WebDriver session is no longer valid");
        return;
      }

      // Use ScreenshotUtils to take the screenshot and get the absolute path
      String screenshotPath = ScreenshotUtils.takeScreenshot(driver, SCREENSHOTS_FOLDER + "/" + name);
      if (screenshotPath != null) {
        // Add to the manual report
        String relativePath = new File(screenshotPath).getAbsolutePath();
        ExtentTest test = extentTest.get();
        if (test != null) {
          test.addScreenCaptureFromPath(relativePath);
        }
      }
    } catch (Exception e) {
      LoggerUtils.error("Failed to capture screenshot", e);
    }
  }

  /**
   * Checks if a WebDriver session is still valid
   */
  private static boolean isDriverValid(WebDriver driver) {
    try {
      driver.getCurrentUrl();
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  public static void logInfo(String message) {
    if (extentTest.get() != null) {
      extentTest.get().info(message);
    }
  }

  public static void logPass(String message) {
    if (extentTest.get() != null) {
      extentTest.get().pass(message);
    }
  }

  public static void logFail(String message) {
    if (extentTest.get() != null) {
      extentTest.get().fail(message);
    }
  }

  public static void logError(String message, Throwable throwable) {
    if (extentTest.get() != null) {
      extentTest.get().fail(message + "\n" + throwable.getMessage());
    }
  }

  // Get the path to screenshots folder for the current test run
  public static String getScreenshotsFolder() {
    return SCREENSHOTS_FOLDER;
  }
}
