package utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;
import java.time.Duration;
import java.util.Map;

public class ApiManager {
  private static final HttpClient httpClient = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(10)).build();
  private static final ObjectMapper mapper = new ObjectMapper();

  public static HttpResponse<String> get(String url, Map<String, String> headers) {
    try {
      HttpRequest.Builder requestBuilder = HttpRequest.newBuilder().uri(URI.create(url)).GET()
        .timeout(Duration.ofSeconds(30));

      if (headers != null) {
        headers.forEach(requestBuilder::header);
      }

      return httpClient.send(requestBuilder.build(), HttpResponse.BodyHandlers.ofString());
    } catch (Exception e) {
      LoggerUtils.error("GET request failed for URL: " + url, e);
      throw new RuntimeException("GET request failed", e);
    }
  }

  public static HttpResponse<String> post(String url, Object body, Map<String, String> headers) {
    try {
      String jsonBody = mapper.writeValueAsString(body);
      HttpRequest.Builder requestBuilder = HttpRequest.newBuilder().uri(URI.create(url))
        .POST(HttpRequest.BodyPublishers.ofString(jsonBody)).timeout(Duration.ofSeconds(30));

      if (headers != null) {
        headers.forEach(requestBuilder::header);
      }

      return httpClient.send(requestBuilder.build(), HttpResponse.BodyHandlers.ofString());
    } catch (Exception e) {
      LoggerUtils.error("POST request failed for URL: " + url, e);
      throw new RuntimeException("POST request failed", e);
    }
  }

  public static HttpResponse<String> put(String url, Object body, Map<String, String> headers) {
    try {
      String jsonBody = mapper.writeValueAsString(body);
      HttpRequest.Builder requestBuilder = HttpRequest.newBuilder().uri(URI.create(url))
        .PUT(HttpRequest.BodyPublishers.ofString(jsonBody)).timeout(Duration.ofSeconds(30));

      if (headers != null) {
        headers.forEach(requestBuilder::header);
      }

      return httpClient.send(requestBuilder.build(), HttpResponse.BodyHandlers.ofString());
    } catch (Exception e) {
      LoggerUtils.error("PUT request failed for URL: " + url, e);
      throw new RuntimeException("PUT request failed", e);
    }
  }

  public static HttpResponse<String> delete(String url, Map<String, String> headers) {
    try {
      HttpRequest.Builder requestBuilder = HttpRequest.newBuilder().uri(URI.create(url)).DELETE()
        .timeout(Duration.ofSeconds(30));

      if (headers != null) {
        headers.forEach(requestBuilder::header);
      }

      return httpClient.send(requestBuilder.build(), HttpResponse.BodyHandlers.ofString());
    } catch (Exception e) {
      LoggerUtils.error("DELETE request failed for URL: " + url, e);
      throw new RuntimeException("DELETE request failed", e);
    }
  }

  public static <T> T parseResponse(HttpResponse<String> response, Class<T> valueType) {
    try {
      return mapper.readValue(response.body(), valueType);
    } catch (Exception e) {
      LoggerUtils.error("Failed to parse response", e);
      throw new RuntimeException("Failed to parse response", e);
    }
  }
}
