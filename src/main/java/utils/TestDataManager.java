package utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class TestDataManager {
  private static final ObjectMapper mapper = new ObjectMapper();
  private static JsonNode testData;
  private static final Random random = new Random();

  static {
    try {
      String dataFile = String.format("src/test/resources/testdata/%s.json", EnvironmentManager.getEnvironment());
      testData = mapper.readTree(new File(dataFile));
      LoggerUtils.info("Loaded test data for environment: " + EnvironmentManager.getEnvironment());
    } catch (IOException e) {
      LoggerUtils.error("Failed to load test data", e);
      throw new RuntimeException("Failed to load test data", e);
    }
  }

  public static String getTestData(String category, String key) {
    try {
      return testData.get(category).get(key).asText();
    } catch (Exception e) {
      LoggerUtils.warn("Test data not found for category: " + category + ", key: " + key);
      return "";
    }
  }

  public static int getTestDataInt(String category, String key) {
    try {
      return testData.get(category).get(key).asInt();
    } catch (Exception e) {
      LoggerUtils.warn("Test data not found for category: " + category + ", key: " + key);
      return 0;
    }
  }

  public static String getRandomTestData(String category, String key) {
    try {
      JsonNode array = testData.get(category).get(key);
      if (array.isArray()) {
        int index = random.nextInt(array.size());
        return array.get(index).asText();
      }
      return array.asText();
    } catch (Exception e) {
      LoggerUtils.warn("Test data not found for category: " + category + ", key: " + key);
      return "";
    }
  }

  public static String generateRandomEmail() {
    return "test" + System.currentTimeMillis() + "@example.com";
  }

  public static String generateRandomString(int length) {
    String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    StringBuilder sb = new StringBuilder(length);
    for (int i = 0; i < length; i++) {
      sb.append(chars.charAt(random.nextInt(chars.length())));
    }
    return sb.toString();
  }
}
