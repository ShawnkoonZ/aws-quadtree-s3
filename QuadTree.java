/**
 * QuadTree DS File.
 * Composed with QuadTree Datastructure.
 */
public class QuadTree {
   private double xMin, xMax, yMin, yMax;
   private int index;
  
   public QuadTree(){
      this.xMin = -1000;
      this.xMax = 1000;
      this.yMin = -1000;
      this.yMax = 1000;
      this.index = 0;
   }

   public QuadTree(double xLow, double xHigh, double yLow, double yHigh){
      this.xMin = xLow;
      this.xMax = xHigh;
      this.yMin = yLow;
      this.yMax = yHigh;
      this.index = 0;
   }
   
   public void generateQuadTree(){
      System.out.println("Index: <Node Number>, [(<xMin>,<xMax>),(<yMin>,<yMax>)]");
      this.generateQuadTree(this.xMin, this.xMax, this.yMin, this.yMax);
   }
   
   private boolean isWithinGap(int gap){
      return (this.xMax - this.xMin == gap) || (this.yMax - this.yMin == gap);
   }
   
   private void generateQuadTree(double xMin, double yMin, double xMax, double yMax){                         
      if(this.isWithinGap(1)){return;}

      System.out.println("Index: " + this.index++ + ", [(" + xMin + 
                         "," + yMin + "),(" + xMax + 
                         "," + yMax + ")]");      
      
      this.generateQuadTree(xMin, (yMax - yMin) / 2,
                            (xMax - xMin) / 2, yMax);
                            
      this.generateQuadTree(xMin, yMin, (xMax - xMin) / 2,
                            (yMax - yMin) / 2);
                            
      this.generateQuadTree((xMax - xMin) / 2, yMin, xMax,
                            (yMax - yMin) / 2);
                            
      this.generateQuadTree((xMin - yMin) / 2, (yMin - yMax) / 2,
                            yMin, yMax);
   }
}