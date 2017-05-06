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
  private long partitionLimit, nodeNumber;

  private void init(double xLow, double yLow, double xHigh, double yHigh, double minimumGap) {
    this.nodeNumber = 0;
    this.rootNode = new TreeNode(xLow, yLow, xHigh, yHigh);
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

  public void generateQuadTree(long partitionLimit) {
    Queue<TreeNode> treeQueue = new LinkedList<TreeNode>();
    treeQueue.add(this.rootNode);
    TreeNode curNode = treeQueue.poll();

    while(curNode != null) {
      System.out.printf("Node : %d | (%f, %f, %f, %f)\n", this.nodeNumber++, curNode.xLow, curNode.yLow, curNode.xHigh, curNode.yHigh);

      if (!curNode.isLeafNode()) {
        // Q1
        treeQueue.add(new TreeNode((curNode.xHigh - curNode.xLow)/2 + curNode.xLow, (curNode.yHigh - curNode.yLow)/2 + curNode.yLow, curNode.xHigh, curNode.yHigh));
        // Q2
        treeQueue.add(new TreeNode(curNode.xLow, (curNode.yHigh - curNode.yLow)/2 + curNode.yLow, (curNode.xHigh - curNode.xLow)/2  + curNode.xLow, curNode.yHigh));
        // Q3
        treeQueue.add(new TreeNode(curNode.xLow, curNode.yLow, (curNode.xHigh - curNode.xLow)/2 + curNode.xLow, (curNode.yHigh - curNode.yLow)/2 + curNode.yLow));
        // Q4
        treeQueue.add(new TreeNode((curNode.xHigh - curNode.xLow)/2 + curNode.xLow, curNode.yLow, curNode.xHigh, (curNode.yHigh - curNode.yLow)/2 + curNode.yLow));
      }
      curNode = treeQueue.poll();
    }
    System.out.println("# Total Elements : " + this.nodeNumber);
  }

  private class TreeNode {
    private double xLow, yLow, xHigh, yHigh;
    private TreeNode(double xLow, double yLow, double xHigh, double yHigh) {
      this.xLow = xLow;
      this.yLow = yLow;
      this.xHigh = xHigh;
      this.yHigh = yHigh;
    }

    private boolean isLeafNode() {
      return (this.xHigh - this.xLow) == 1 || (this.yHigh - this.yLow) == 1;
    }
  }
}