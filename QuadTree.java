/**
 * QuadTree DS File.
 * Composed with QuadTree Datastructure.
 */
public class QuadTree {
   private double xMin, xMax, yMin, yMax;
   private int index;
   private int gap;
  
   public QuadTree(){
      this.xMin = -1000;
      this.xMax = 1000;
      this.yMin = -1000;
      this.yMax = 1000;
      this.index = 0;
      this.gap = 1;
   }

   public QuadTree(double xLow, double xHigh, double yLow, double yHigh, int gap){
      this.xMin = xLow;
      this.xMax = xHigh;
      this.yMin = yLow;
      this.yMax = yHigh;
      this.index = 0;
      this.gap = gap;
   }
   
   public void generateQuadTree(){
      System.out.println("Index: <Node Number>, [(<xMin>,<xMax>),(<yMin>,<yMax>)]");
      this.generateQuadTree(this.xMin, this.xMax, this.yMin, this.yMax);
   }
   
   private boolean isWithinGap(int gap, double xMin, double yMin, double xMax, double yMax){
      return (xMax - xMin == gap) || (yMax - yMin == gap);
   }
   
   private void generateQuadTree(double xMin, double yMin, double xMax, double yMax){                         
      System.out.println("Index: " + this.index++ + ", [(" + xMin + 
                         "," + yMin + "),(" + xMax + 
                         "," + yMax + ")]");      
                         
      if(this.isWithinGap(this.gap,xMin,yMin,xMax,yMax)){return;}
      
      //NW
      this.generateQuadTree(xMin, (yMax - yMin) / 2 + yMin,
                            (xMax - xMin) / 2 + xMin, yMax);
                            
      //SW
      this.generateQuadTree(xMin, yMin, (xMax - xMin) / 2 + xMin,
                            (yMax - yMin) / 2 + yMin);
      
      //SE
      this.generateQuadTree((xMax - xMin) / 2 + xMin, yMin, xMax,
                            (yMax - yMin) / 2 + yMin);
      
      //NE                      
      this.generateQuadTree((xMax - xMin) / 2 + xMin, (yMax - yMin) / 2 + yMin,
                            xMax, yMax);
   }
}