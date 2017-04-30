/**
 * Tester File.
 * Composed with User logics.
 */
public class QuadTreeTester {
   public static void main(String[] args) {
      QuadTree tree = new QuadTree(0,0,64,64,1);
      tree.generateQuadTree();  
   }
}