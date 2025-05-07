package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class EnvironmentManager {
  private static final Properties properties = new Properties();
  private static String environment;

  static {
    try {
      environment = System.getProperty("env", "qa");
      String configPath = String.format("src/test/resources/config/%s.properties", environment);
      properties.load(new FileInputStream(configPath));
      LoggerUtils.info("Loaded configuration for environment: " + environment);
    } catch (IOException e) {
      LoggerUtils.error("Failed to load environment configuration", e);
      throw new RuntimeException("Failed to load environment configuration", e);
    }
  }

  public static String getProperty(String key) {
    String value = properties.getProperty(key);
    if (value == null) {
      LoggerUtils.warn("Property not found: " + key);
      return "";
    }
    return value;
  }

  public static String getEnvironment() {
    return environment;
  }

  public static boolean isProduction() {
    return "prod".equalsIgnoreCase(environment);
  }

  public static boolean isQA() {
    return "qa".equalsIgnoreCase(environment);
  }

  public static boolean isStaging() {
    return "staging".equalsIgnoreCase(environment);
  }
}
