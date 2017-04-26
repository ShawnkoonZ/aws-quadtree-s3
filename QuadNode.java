public class QuadNode{
   private double x, y;
   private QuadNode NE, NW, SW, SE;
   private double data;
   
   public QuadNode(double xCoordinate, double yCoordinate, double value){
      this.x = xCoordinate;
      this.y = yCoordinate;
      this.data = value;
   }
   
   public double getXCoordinate(){
      return this.x;
   }
   
   public double getYCoordinate(){
      return this.y;
   }
   
   public double[] getCoordinates(){
      double[] coordinateArray = {this.x, this.y};
      return coordinateArray;
   }
   
   public double getData(){
      return this.data;
   }
   
   public QuadNode getQuadNodeNE(){
      return this.NE;
   }
   
   public QuadNode getQuadNodeNW(){
      return this.NW;
   }
   
   public QuadNode getQuadNodeSW(){
      return this.SW;
   }
   
   public QuadNode getQuadNodeSE(){
      return this.SE;
   }
   
   public QuadNode[] getQuadNodes(){
      QuadNode[] nodeArray = {this.NE, this.NW, this.SW, this.SE};
      return nodeArray;
   }
   
   public void setXCoordinate(double xPos){
      this.x = xPos;
   }
   
   public void setYCoordinate(double yPos){
      this.y = yPos;
   }
   
   public void setData(double value){
      this.data = value;
   }
   
   public void setQuadNodeNE(QuadNode NE){
      this.NE = NE;
   }
   
   public void setQuadNodeNW(QuadNode NW){
      this.NW = NW;
   }
   
   public void setQuadNodeSW(QuadNode SW){
      this.SW = SW;
   }
   
   public void setQuadNodeSE(QuadNode SE){
      this.SE = SE;
   }
}