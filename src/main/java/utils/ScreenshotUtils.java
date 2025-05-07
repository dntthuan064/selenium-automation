package utils;

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

public class ScreenshotUtils {

  public static String takeScreenshot(WebDriver driver, String testName) {
    String screenshotPath = ConfigManager.getProperty("screenshot.path");
    String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
    String fileName = testName + "_" + timestamp + ".png";

    try {
      Path path = Paths.get(screenshotPath);
      if (!Files.exists(path)) {
        Files.createDirectories(path);
      }

      File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
      File destFile = new File(screenshotPath + fileName);
      Files.copy(screenshot.toPath(), destFile.toPath());

      return destFile.getAbsolutePath();
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }
}
