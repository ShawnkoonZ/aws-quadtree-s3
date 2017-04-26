/**
 * Tester File.
 * Composed with User logics.
 */
public class QuadTreeTester {
  public static void main(String[] args) {
    QuadTree tree = new QuadTree();
    tree.insert(100,100,5);
    tree.insert(99,200,3);
    tree.insert(-4949,39038,45);//The coordinates [-4949.0,39038.0] are out of bounds! Skipping insertion...
  }
}