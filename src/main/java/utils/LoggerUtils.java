package utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerUtils {
  private static final Logger LOGGER = LoggerFactory.getLogger(LoggerUtils.class);

  public static void info(String message) {
    LOGGER.info(message);
  }

  public static void info(String message, Object... args) {
    LOGGER.info(message, args);
  }

  public static void error(String message) {
    LOGGER.error(message);
  }

  public static void error(String message, Throwable throwable) {
    LOGGER.error(message, throwable);
  }

  public static void error(String message, Object... args) {
    LOGGER.error(message, args);
  }

  public static void warn(String message) {
    LOGGER.warn(message);
  }

  public static void warn(String message, Object... args) {
    LOGGER.warn(message, args);
  }

  public static void debug(String message) {
    LOGGER.debug(message);
  }

  public static void debug(String message, Object... args) {
    LOGGER.debug(message, args);
  }

  public static void trace(String message) {
    LOGGER.trace(message);
  }

  public static void trace(String message, Object... args) {
    LOGGER.trace(message, args);
  }
}
