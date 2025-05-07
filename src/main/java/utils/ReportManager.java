package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ReportManager {
  private static final ExtentReports extentReports = new ExtentReports();
  private static final ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();
  private static final String REPORT_DIR = "test-reports";
  private static final String SCREENSHOT_DIR = "screenshots";
  private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");

  static {
    setupExtentReports();
  }

  private static void setupExtentReports() {
    String timestamp = LocalDateTime.now().format(DATE_FORMATTER);
    String reportPath = String.format("%s/%s/extent-reports", REPORT_DIR, timestamp);

    // Create directories if they don't exist
    createDirectories(reportPath);
    createDirectories(String.format("%s/%s/%s", REPORT_DIR, timestamp, SCREENSHOT_DIR));

    ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath + "/extent-report.html");
    sparkReporter.config().setReportName("Web Form Automation Test Report");
    sparkReporter.config().setDocumentTitle("Test Execution Report");

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
    try {
      String timestamp = LocalDateTime.now().format(DATE_FORMATTER);
      String screenshotPath = String.format("%s/%s/%s/%s.png", REPORT_DIR, timestamp, SCREENSHOT_DIR, name);

      File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
      Files.copy(screenshot.toPath(), Paths.get(screenshotPath));

      extentTest.get().addScreenCaptureFromPath(screenshotPath);
    } catch (IOException e) {
      LoggerUtils.error("Failed to capture screenshot", e);
    }
  }

  public static void logInfo(String message) {
    extentTest.get().info(message);
  }

  public static void logPass(String message) {
    extentTest.get().pass(message);
  }

  public static void logFail(String message) {
    extentTest.get().fail(message);
  }

  public static void logError(String message, Throwable throwable) {
    extentTest.get().fail(message + "\n" + throwable.getMessage());
  }
}
