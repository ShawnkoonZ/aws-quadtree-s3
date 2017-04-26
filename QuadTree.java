/**
 * QuadTree DS File.
 * Composed with QuadTree Datastructure.
 */
public class QuadTree {
  private double xMin, xMax, yMin, yMax;
  private QuadNode root;
  
  public QuadTree(){
   this.xMin = -1000;
   this.xMax = 1000;
   this.yMin = -1000;
   this.yMax = 1000;
  }

  public QuadTree(double xLow, double xHigh, double yLow, double yHigh) {
    this.xMin = xLow;
    this.xMax = xHigh;
    this.yMin = yLow;
    this.yMax = yHigh;
  }
  
  public void insert(double xCoordinate, double yCoordinate, double value){
    boolean isWithinBounds = this.checkBounds(xCoordinate, yCoordinate);
    
    if(!isWithinBounds){
      System.out.println("The coordinates [" + xCoordinate + "," + yCoordinate + "] are out of bounds! Skipping insertion...");
      return;
    }
    
    this.root = this.insert(this.root, xCoordinate, yCoordinate, value);
  }
  
  private QuadNode insert(QuadNode node, double xCoordinate, double yCoordinate, double value){  
    if(node == null){
      return new QuadNode(xCoordinate, yCoordinate, value);
    }
    
    if((xCoordinate > node.getXCoordinate()) && (yCoordinate > node.getYCoordinate())){
      node.setQuadNodeNE(insert(node.getQuadNodeNE(), xCoordinate, yCoordinate, value));
    }
    
    if((xCoordinate < node.getXCoordinate()) && (yCoordinate > node.getYCoordinate())){
      node.setQuadNodeNW(insert(node.getQuadNodeNW(), xCoordinate, yCoordinate, value));
    }
    
    if((xCoordinate < node.getXCoordinate()) && (yCoordinate < node.getYCoordinate())){
      node.setQuadNodeSW(insert(node.getQuadNodeSW(), xCoordinate, yCoordinate, value));
    }
    
    if((xCoordinate > node.getXCoordinate()) && (yCoordinate < node.getYCoordinate())){
      node.setQuadNodeSE(insert(node.getQuadNodeSE(), xCoordinate, yCoordinate, value));
    }
    
    return node;
  }
  
  private boolean checkBounds(double xCoordinate, double yCoordinate){
    return xCoordinate >= this.xMin && xCoordinate <= this.xMax && yCoordinate >= this.yMin && yCoordinate <= this.yMax;
  }
}