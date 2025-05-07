package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigManager {
  private static Properties properties;
  private static final String CONFIG_FILE = "src/test/resources/config.properties";

  static {
    try {
      properties = new Properties();
      FileInputStream fis = new FileInputStream(CONFIG_FILE);
      properties.load(fis);
      fis.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static String getProperty(String key) {
    return properties.getProperty(key);
  }

  public static int getIntProperty(String key) {
    return Integer.parseInt(properties.getProperty(key));
  }

  public static boolean getBooleanProperty(String key) {
    return Boolean.parseBoolean(properties.getProperty(key));
  }
}
