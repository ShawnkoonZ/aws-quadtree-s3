/**
 * Tester File.
 * Composed with User logics.
 */

// Utils
import java.io.IOException;

// Version Imports
import version1.*;

public class QuadTreeTester {
   public static void main(String[] args) throws IOException {
      this.executeVersion_2();
   }

   private static void executeVersion_1() {
    QuadTree tree = new QuadTree(0,0,8,8,1);
      try{
         tree.generateQuadTree(10);
      }
      catch(IOException error){
         System.out.println(error);
      }
   }

   private static void executeVersion_2() {
     System.out.println("Please Implement the solution.");
   }
}