package commons;

public class TestLog {

  public static void stepInfo(String message) {
    System.out.println("[STEP] " + message);
  }

  public static void testPass(String message) {
    System.out.println("[PASS] " + message);
  }

  public static void testFail(String message) {
    System.err.println("[FAIL] " + message);
  }
}