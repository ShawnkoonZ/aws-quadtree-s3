/**
 * QuadTree DS File.
 * Composed with QuadTree Datastructure.
 */

package version1;

import java.lang.Math;
import java.io.IOException;

public class QuadTree {
   private double xMin, xMax, yMin, yMax;
   private int index, gap, numberOfFiles, numberOfNodes, nodeCounter, fileCounter;
   private String prefix, fileExtension; 
   private FileUtil treeBuilder;
  
   public QuadTree(){
      this.initDefaultQuadTree();
   }

   public QuadTree(double xLow, double yLow, double xHigh, double yHigh, int gap){
      if(this.isPowerOfTwo(xHigh) || this.isPowerOfTwo(yHigh)){
         this.initCustomQuadTree(xLow, yLow, xHigh, yHigh, gap);
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
      this.prefix = "aws";
      this.fileExtension = "csv";
      this.treeBuilder = new FileUtil(this.prefix);
   }
   
   public void initCustomQuadTree(double xLow, double yLow, double xHigh, double yHigh, int gap){
      this.xMin = xLow;
      this.yMin = yLow;
      this.xMax = xHigh;
      this.yMax = yHigh;
      this.index = 0;
      this.gap = gap;
      this.prefix = "aws";
      this.fileExtension = "csv";
      this.treeBuilder = new FileUtil(this.prefix);
   }
   
   public void generateQuadTree(int partitionLimit) throws IOException{
      System.out.println("Index: <Node Number>, [(<xMin>,<yMin>),(<xMax>,<yMax>)]");
      double maxCoordinate = 0.0;
      if(this.isPowerOfTwo(this.xMax)){
         maxCoordinate = this.xMax;
      }
      else if(this.isPowerOfTwo(this.yMax)){
         maxCoordinate = this.yMax;
      }
      else{
         System.out.println("Error: maximum coordinate(s) are not a power of 2!");
         System.exit(-1);
      }
      this.numberOfNodes = this.getTotalNodes(maxCoordinate);
      this.numberOfFiles = this.treeBuilder.calculateNumberOfFiles(numberOfNodes, partitionLimit);
      this.nodeCounter = 0;
      this.fileCounter = 0;
      try{
         if(partitionLimit < 1){partitionLimit = 10;}
         this.generateQuadTree(this.xMin, this.yMin, this.xMax, this.yMax, partitionLimit);
      }
      catch(IOException error){
         System.out.println(error);
      }
   }
   
   public void setFileExtension(String extension){
      this.fileExtension = extension;
   }
   
   public void setPrefix(String prefix){
      this.prefix = prefix;
   }
   
   private String buildNode(int index, double xMin, double yMin, double xMax, double yMax){
      if(this.fileExtension.equals("csv")){
         String node = "";
         
         if(this.nodeCounter == 0){
            node += "Index\txMin\tyMin\txMax\tyMax\n";   
         }
         
         node += (this.index++) + "\t" + xMin + "\t" + yMin + "\t" + xMax + "\t" + yMax;
         
         return node;
      }
      else{
         return "Index: " + (this.index++) + ", [(" + xMin + "," + yMin + "),(" + xMax + "," + yMax + ")]";
      }
   }
   
   private void generateQuadTree(double xMin, double yMin, double xMax, double yMax, int partitionLimit) throws IOException{                         
      String node = this.buildNode(this.index, xMin, yMin, xMax, yMax);
      
      System.out.println(node);    
      this.nodeCounter++;
      
      try{
         this.treeBuilder.bufferAdd(node);
         
         if(this.nodeCounter == partitionLimit || (index - 1) == this.numberOfNodes){
            this.fileCounter++;
            this.treeBuilder.buildFile(this.fileExtension, this.fileCounter);
            this.treeBuilder.bufferClear();
            
            if(this.fileCounter <= this.numberOfFiles){
               this.nodeCounter = 0;
            }
         }
      }
      catch(IOException error){
         System.out.println(error);
      }
                         
      if(this.isWithinGap(this.gap,xMin,yMin,xMax,yMax)){return;}
      
      this.generateQuadTree((xMax-xMin)/2 + xMin, (yMax-yMin)/2 + yMin, xMax, yMax, partitionLimit); //NE QI
      this.generateQuadTree(xMin, (yMax-yMin)/2 + yMin, (xMax-xMin)/2 + xMin, yMax, partitionLimit); //NW QII
      this.generateQuadTree(xMin, yMin, (xMax-xMin)/2 + xMin, (yMax-yMin)/2 + yMin, partitionLimit); //SW QIII
      this.generateQuadTree((xMax-xMin)/2 + xMin, yMin, xMax, (yMax-yMin)/2 + yMin, partitionLimit); //SE QIV                
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
   
   private boolean isPowerOfTwo(double realNumber){
      return true;
   }
   
   private boolean isWithinGap(int gap, double xMin, double yMin, double xMax, double yMax){
      return (xMax - xMin == gap) || (yMax - yMin == gap);
   }
}
