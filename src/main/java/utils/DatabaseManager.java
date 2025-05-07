package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DatabaseManager {
  private static Connection connection;

  static {
    try {
      String url = EnvironmentManager.getProperty("db.url");
      String username = EnvironmentManager.getProperty("db.username");
      String password = EnvironmentManager.getProperty("db.password");

      connection = DriverManager.getConnection(url, username, password);
      LoggerUtils.info("Database connection established");
    } catch (SQLException e) {
      LoggerUtils.error("Failed to establish database connection", e);
      throw new RuntimeException("Failed to establish database connection", e);
    }
  }

  public static List<Map<String, Object>> executeQuery(String query, Object... params) {
    List<Map<String, Object>> results = new ArrayList<>();

    try (PreparedStatement stmt = connection.prepareStatement(query)) {
      for (int i = 0; i < params.length; i++) {
        stmt.setObject(i + 1, params[i]);
      }

      try (ResultSet rs = stmt.executeQuery()) {
        while (rs.next()) {
          Map<String, Object> row = new HashMap<>();
          for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
            row.put(rs.getMetaData().getColumnName(i), rs.getObject(i));
          }
          results.add(row);
        }
      }
    } catch (SQLException e) {
      LoggerUtils.error("Failed to execute query: " + query, e);
      throw new RuntimeException("Failed to execute query", e);
    }

    return results;
  }

  public static int executeUpdate(String query, Object... params) {
    try (PreparedStatement stmt = connection.prepareStatement(query)) {
      for (int i = 0; i < params.length; i++) {
        stmt.setObject(i + 1, params[i]);
      }
      return stmt.executeUpdate();
    } catch (SQLException e) {
      LoggerUtils.error("Failed to execute update: " + query, e);
      throw new RuntimeException("Failed to execute update", e);
    }
  }

  public static void closeConnection() {
    if (connection != null) {
      try {
        connection.close();
        LoggerUtils.info("Database connection closed");
      } catch (SQLException e) {
        LoggerUtils.error("Failed to close database connection", e);
      }
    }
  }

  public static void rollback() {
    if (connection != null) {
      try {
        connection.rollback();
        LoggerUtils.info("Database transaction rolled back");
      } catch (SQLException e) {
        LoggerUtils.error("Failed to rollback transaction", e);
      }
    }
  }
}
