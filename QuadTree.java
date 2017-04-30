/**
 * QuadTree DS File.
 * Composed with QuadTree Datastructure.
 */
 
import java.lang.Math;

public class QuadTree {
   private double xMin, xMax, yMin, yMax;
   private int index;
   private int gap;
  
   public QuadTree(){
      this.initDefaultQuadTree();
   }

   public QuadTree(double xLow, double xHigh, double yLow, double yHigh, int gap){
      if(this.isPowerOfTwo(xHigh, yHigh)){
         this.initCustomQuadTree(xLow, xHigh, yLow, yHigh, gap);
      }
      else{
         System.out.println("Unable to instantiate a QuadTree with the paramaters you defined!\n" +
                            "Make sure that either your xMax or yMax are a power of 2.\n" +
                            "Instantiating a QuadTree with (0,0,64,64,1) instead.\n"
                           );
         this.initDefaultQuadTree();
      }
   }
   
   public void initDefaultQuadTree(){
      this.xMin = 0;
      this.yMin = 0;
      this.xMax = 64;
      this.yMax = 64;
      this.index = 0;
      this.gap = 1;
   }
   
   public void initCustomQuadTree(double xLow, double yLow, double xHigh, double yHigh, int gap){
      this.xMin = xLow;
      this.yMin = yLow;
      this.xMax = xHigh;
      this.yMax = yHigh;
      this.index = 0;
      this.gap = gap;
   }
   
   public void generateQuadTree(){
      System.out.println("Index: <Node Number>, [(<xMin>,<yMin>),(<xMax>,<yMax>)]");
      this.generateQuadTree(this.xMin, this.yMin, this.xMin, this.yMax);
   }
   
   private void generateQuadTree(double xMin, double yMin, double xMax, double yMax){                         
      System.out.println("Index: " + (this.index++) + ", [(" + xMin + "," + yMin + "),(" + xMax + "," + yMax + ")]");      
                         
      if(this.isWithinGap(this.gap,xMin,yMin,xMax,yMax)){return;}
      
      this.generateQuadTree((xMax-xMin)/2 + xMin, (yMax-yMin)/2 + yMin, xMax, yMax); //NE QI
      this.generateQuadTree(xMin, (yMax-yMin)/2 + yMin, (xMax-xMin)/2 + xMin, yMax); //NW QII
      this.generateQuadTree(xMin, yMin, (xMax-xMin)/2 + xMin, (yMax-yMin)/2 + yMin); //SW QIII
      this.generateQuadTree((xMax-xMin)/2 + xMin, yMin, xMax, (yMax-yMin)/2 + yMin); //SE QIV                
   }
   
   private int getTotalNodes(double maxCoordinate){
      double base = 2.0;
      double exponentH = Math.log(maxCoordinate)/Math.log(base); //log base b of n = log base e of n / log base e of b
      int totalNodes = 0;
      
      for(int i = 1; i <= exponentH; i++){
         totalNodes += Math.pow(4,i);
      }
      
      return totalNodes;
   }
   
   private boolean isPowerOfTwo(double xMax, double yMax){
      return true;
   }
   
   private boolean isWithinGap(int gap, double xMin, double yMin, double xMax, double yMax){
      return (xMax - xMin == gap) || (yMax - yMin == gap);
   }
}
