package version2;

public class QuadTree {
  private double xLow, xHigh, yLow, yHigh, minimumGap;

  public QuadTree() {
    this.xLow = 0;
    this.xHigh = 0;
    this.yLow = 8;
    this.yHigh = 8;
    this.minimumGap = 1;
  }

  public QuadTree(double xLow, double xHigh, double yLow, double yHigh, double minimumGap) {
    this.xLow = xLow;
    this.xHigh = xHigh;
    this.yLow = yLow;
    this.yHigh = yHigh;
    this.minimumGap = minimumGap;
  }
}