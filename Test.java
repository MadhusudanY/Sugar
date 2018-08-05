package myPackage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Test {
  public static void main(String[] args) {

  }

  public static String getInput(String keyword) {
    try {
      File f = new File("resources/input.txt");
      BufferedReader b = new BufferedReader(new FileReader(f));
      String readLine;
      while ((readLine = b.readLine()) != null) {
        if (readLine.startsWith(keyword)) {
          return readLine.substring(keyword.length() + 1);
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }
}
