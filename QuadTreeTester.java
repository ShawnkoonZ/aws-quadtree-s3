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
    double xLow = 0;
    double yLow = 0;
    double xHigh = 8;
    double yHigh = 8;
    double minimumGap = 1;
    String bucketName = "ewu.quadtree.s3";
    int partitionLimit = 10;

    QuadTree tree = new QuadTree(xLow, yLow, xHigh, yHigh, minimumGap, bucketName);

    System.out.println("=> Process begin...");
    tree.generateQuadTree(partitionLimit);
    System.out.println("=> Tree generating process finished...");
  }
}
