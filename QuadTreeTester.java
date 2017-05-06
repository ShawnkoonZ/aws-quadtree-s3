/**
 * Tester File.
 * Composed with User logics.
 */

// Utils
import java.io.IOException;

// Version Imports
// import version1.*;
import version2.*;

public class QuadTreeTester {
  public static void main(String[] args) throws IOException {
    QuadTree tree = new QuadTree(0,0,8,8,1);
    try {
      System.out.println("=> Process begin...");
      tree.generateQuadTree(10);
      System.out.println("=> Tree generating process finished...");
    }
    catch(IOException error)  {
      System.out.println(error);
    }
  }
}