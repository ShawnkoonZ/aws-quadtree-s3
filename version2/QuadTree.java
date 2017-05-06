package version2;

import java.util.*;

public class QuadTree {
  private TreeNode curNode;
  private double minimumGap;
  private long partitionLimit, nodeNumber;

  private void init(double xLow, double yLow, double xHigh, double yHigh, double minimumGap) {
    this.nodeNumber = 0;
    curNode = new TreeNode(xLow, yLow, xHigh, yHigh);
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
    if (this.curNode.xHigh > 0 && this.curNode.yHigh > 0) {
      if(this.curNode.xHigh == this.curNode.yHigh) {
        if (this.isPowerTwo((int)this.curNode.xHigh)) {
          if(!this.isPowerTwo((int)this.curNode.yHigh)) {
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
    System.out.printf("Node : %d, (%f, %f, %f, %f)\n", this.nodeNumber, this.curNode.xLow, this.curNode.yLow, this.curNode.xHigh, this.curNode.yHigh);
    Queue<TreeNode> hi = new LinkedList<TreeNode>();

    
  }

  private class TreeNode {
    private double xLow, yLow, xHigh, yHigh;
    public TreeNode(double xLow, double yLow, double xHigh, double yHigh) {
      this.xLow = xLow;
      this.yLow = yLow;
      this.xHigh = xHigh;
      this.yHigh = yHigh;
    }
  }
}