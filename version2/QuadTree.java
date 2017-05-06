package version2;

public class QuadTree {
  private double xLow, yLow, xHigh, yHigh, minimumGap;
  private int partitionLimit;

  private init(double xLow, double yLow, double xHigh, double yHigh, double minimumGap) {
    this.xLow = xLow;
    this.yLow = yLow;
    this.xHigh = xHigh;
    this.yHigh = yHigh;
    this.minimumGap = minimumGap;
    this.checkForExit();
  }

  public QuadTree() {
    this.init(0,0,8,8,1);
  }

  public QuadTree(double xLow, double yLow, double xHigh, double yHigh, double minimumGap) {
    this.init(xLow, yLow, xHigh, yHigh, minimumGap);
  }
  
	private boolean isPowerTwo(double number) { return (number & (number - 1)) == 0; }

  private void checkForExit() {
    if (this.xHigh > 0 && this.yHigh > 0) {
      if(this.xHigh == this.yHigh) {
        if (this.isPowerTwo(this.xHigh)) {
          if(!this.isPowerTwo(this.yHigh)) {
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
    
  }
}