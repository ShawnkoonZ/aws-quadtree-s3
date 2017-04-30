/**
 * Tester File.
 * Composed with User logics.
 */

import java.io.IOException; 

public class QuadTreeTester {
   public static void main(String[] args) throws IOException {
      QuadTree tree = new QuadTree(0,0,8,8,1);
      try{
         tree.generateQuadTree(10); //generate the QuadTree between 10 files
      }
      catch(IOException error){
         System.out.println(error);
      }
      /*tree.initDefaultQuadTree();
      tree.generateQuadTree(10);*/
   }
}