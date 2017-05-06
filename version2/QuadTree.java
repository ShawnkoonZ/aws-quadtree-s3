/*
*  -------------
* |  Q2  |  Q1  |
* |-------------|
* |  Q3  |  Q4  |
*  -------------
*/
package version2;

import java.util.*;

public class QuadTree {
  private TreeNode rootNode;
  private double minimumGap;
  private int partitionLimit, nodeCount, leafCount;
  private String[] nodeCountInBinary = {"00", "01", "10", "11"};
  private String startingBinary = "";

  private void init(double xLow, double yLow, double xHigh, double yHigh, double minimumGap) {
    this.nodeCount = 0;
    this.leafCount = 0;
    this.rootNode = new TreeNode(xLow, yLow, xHigh, yHigh, startingBinary, this.nodeCount);
    this.minimumGap = minimumGap;
    this.checkForExit();
  }

  public QuadTree() {
    this.init(0,0,8,8,1);
  }

  public QuadTree(double xLow, double yLow, double xHigh, double yHigh, double minimumGap) {
    this.init(xLow, yLow, xHigh, yHigh, minimumGap);
  }
  
	private boolean isPowerTwo(int number) { return (number & (number - 1)) == 0; }

  private void checkForExit() {
    if (this.rootNode.xHigh > 0 && this.rootNode.yHigh > 0) {
      if(this.rootNode.xHigh == this.rootNode.yHigh) {
        if (this.isPowerTwo((int)this.rootNode.xHigh)) {
          if(!this.isPowerTwo((int)this.rootNode.yHigh)) {
            System.out.println("Error : High-Y needs to be power of 2.");
            System.exit(-1);
          }
        }
        else {
          System.out.println("Error : High-X needs to be power of 2.");
          System.exit(-1);
        }							
      }
      else {
        System.out.println("Error : High-X & High-Y needs to be equal.");
        System.exit(-1);
      }
    }
    else {
      System.out.println("Error : High-X and High-Y needs to be positive Number.");
      System.exit(-1);
    }
  }

  public void generateQuadTree(int partitionLimit) {
    Queue<TreeNode> treeQueue = new LinkedList<TreeNode>();
    treeQueue.add(this.rootNode);
    TreeNode curNode = treeQueue.poll();

    while(curNode != null) {
      if (!curNode.isLeafNode()) {
        // Q1
        treeQueue.add(
          new TreeNode(
            (curNode.xHigh - curNode.xLow)/2 + curNode.xLow,
            (curNode.yHigh - curNode.yLow)/2 + curNode.yLow,
            curNode.xHigh,
            curNode.yHigh,
            curNode.binaryIndex + nodeCountInBinary[0],
            (curNode.numberIndex * nodeCountInBinary.length) + 1
          )
        );
        // Q2
        treeQueue.add(
          new TreeNode(
            curNode.xLow,
            (curNode.yHigh - curNode.yLow)/2 + curNode.yLow,
            (curNode.xHigh - curNode.xLow)/2  + curNode.xLow,
            curNode.yHigh,
            curNode.binaryIndex + nodeCountInBinary[1],
            (curNode.numberIndex * nodeCountInBinary.length) + 2
          )
        );
        // Q3
        treeQueue.add(
          new TreeNode(
            curNode.xLow,
            curNode.yLow,
            (curNode.xHigh - curNode.xLow)/2 + curNode.xLow,
            (curNode.yHigh - curNode.yLow)/2 + curNode.yLow,
            curNode.binaryIndex + nodeCountInBinary[2],
            (curNode.numberIndex * nodeCountInBinary.length) + 3
          )
        );
        // Q4
        treeQueue.add(
          new TreeNode(
            (curNode.xHigh - curNode.xLow)/2 + curNode.xLow,
            curNode.yLow,
            curNode.xHigh,
            (curNode.yHigh - curNode.yLow)/2 + curNode.yLow,
            curNode.binaryIndex + nodeCountInBinary[3],
            (curNode.numberIndex * nodeCountInBinary.length) + 4
          )
        );
      }
      else {
        System.out.print("\n LEAF ! ");
        this.leafCount++;
      }

      System.out.println(curNode);
      this.nodeCount++;

      curNode = treeQueue.poll();
    }
    System.out.println("# Total Elements : " + this.nodeCount);
    System.out.println("# Total Leaf Elements : " + this.leafCount);
  }

  private class TreeNode {
    private double xLow, yLow, xHigh, yHigh;
    private String binaryIndex;
    private int numberIndex;

    private TreeNode(double xLow, double yLow, double xHigh, double yHigh, String binaryIndex, int numberIndex) {
      this.xLow = xLow;
      this.yLow = yLow;
      this.xHigh = xHigh;
      this.yHigh = yHigh;
      this.binaryIndex = binaryIndex;
      this.numberIndex = numberIndex;
    }

    private boolean isLeafNode() {
      return (this.xHigh - this.xLow) == 1 || (this.yHigh - this.yLow) == 1;
    }

    @Override
    public String toString() {
      return String.format("Node : %d |$| (%.1f, %.1f, %.1f, %.1f) |$| Bin : %s\n", this.numberIndex, this.xLow, this.yLow, this.xHigh, this.yHigh, this.binaryIndex);
    }
  }
}