package utils;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.openqa.selenium.WebDriver;

public class TestListener implements ITestListener {
  @Override
  public void onTestStart(ITestResult result) {
    LoggerUtils.info("Starting test: " + result.getName());
    ReportManager.startTest(result.getName());
  }

  @Override
  public void onTestSuccess(ITestResult result) {
    LoggerUtils.info("Test passed: " + result.getName());
    ReportManager.logPass("Test passed successfully");
    ReportManager.endTest();
  }

  @Override
  public void onTestFailure(ITestResult result) {
    LoggerUtils.error("Test failed: " + result.getName());
    ReportManager.logFail("Test failed: " + result.getThrowable().getMessage());

    try {
      WebDriver driver = DriverManager.getDriver();
      if (driver != null) {
        ReportManager.addScreenshot(driver, "test-failure-" + result.getName());
      }
    } catch (Exception e) {
      LoggerUtils.error("Could not capture failure screenshot", e);
    } finally {
      ReportManager.endTest();
    }
  }

  @Override
  public void onTestSkipped(ITestResult result) {
    LoggerUtils.warn("Test skipped: " + result.getName());
    ReportManager.logInfo("Test skipped: " + result.getThrowable().getMessage());
    ReportManager.endTest();
  }

  @Override
  public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
    LoggerUtils.warn("Test failed but within success percentage: " + result.getName());
    ReportManager.logInfo("Test failed but within success percentage: " + result.getThrowable().getMessage());
    ReportManager.endTest();
  }

  @Override
  public void onStart(ITestContext context) {
    LoggerUtils.info("Starting test suite: " + context.getName());
  }

  @Override
  public void onFinish(ITestContext context) {
    LoggerUtils.info("Finished test suite: " + context.getName());
  }
}
