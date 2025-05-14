package utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ScreenshotUtils {

  public static String takeScreenshot(WebDriver driver, String filePath) {
    try {
      Path path = Paths.get(filePath).getParent();
      if (!Files.exists(path)) {
        Files.createDirectories(path);
      }

      File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
      File destFile = new File(filePath + ".png");
      Files.copy(screenshot.toPath(), destFile.toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);

      return destFile.getAbsolutePath();
    } catch (org.openqa.selenium.WebDriverException e) {
      // WebDriver session is closed or invalid
      LoggerUtils.error("WebDriver session is not available for screenshot: " + e.getMessage());
      return null;
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }
}
