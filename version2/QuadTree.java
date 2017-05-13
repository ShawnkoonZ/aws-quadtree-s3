/*
*  -------------
* |  Q2  |  Q1  |
* |-------------|
* |  Q3  |  Q4  |
*  -------------
*/
package version2;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectSummary;


public class QuadTree {
   private TreeNode rootNode;
   private double minimumGap;
   private int partitionLimit, nodeCounter, leafCounter, fileCounter, numberOfNodes, numberOfFiles, numberOfLeaves;
   private FileUtil treeFileBuilder;
   private String[] nodeCounterInBinary = {"00", "01", "10", "11"};
   private String startingBinary, filePrefix, fileExtension, bucketName;
   private AWSCredentials credentials;
   private AmazonS3 s3;
   private Region awsRegion;

   private void init(double xLow, double yLow, double xHigh, double yHigh, double minimumGap, String bucketName) {
     this.startingBinary = "";
     this.minimumGap = minimumGap;
     
     this.setCounter(0);
     this.setBucketName(bucketName);
     this.setFilePrefix("aws");
     this.setFileExtension("csv");
     this.setPartitionLimit(10);
     
     this.rootNode = new TreeNode(xLow, yLow, xHigh, yHigh, startingBinary, this.nodeCounter);
     this.treeFileBuilder = new FileUtil(this.filePrefix);
     
     this.checkForExit();
     this.numberOfFiles = this.treeFileBuilder.calculateNumberOfFiles(this.numberOfNodes, this.partitionLimit);
   }

   public QuadTree() {
     this.init(0,0,8,8,1,"tf-quadtree-main-bucket");
   }

   public QuadTree(double xLow, double yLow, double xHigh, double yHigh, double minimumGap) {
     this.init(xLow, yLow, xHigh, yHigh, minimumGap, "tf-quadtree-main-bucket");
   }
   
   public QuadTree(double xLow, double yLow, double xHigh, double yHigh, double minimumGap, String bucketName) {
     this.init(xLow, yLow, xHigh, yHigh, minimumGap, bucketName);
   }
   
   public void setFileExtension(String extension){
      this.fileExtension = extension;
   }
   
   public void setFilePrefix(String prefix){
      this.filePrefix = prefix;
   }
   
   public void setPartitionLimit(int upperBound){
      this.partitionLimit = upperBound;
   }  
   
   public void setBucketName(String name){
      this.bucketName = name;
   }
   
   public void generateQuadTree(int partitionLimit) throws IOException {
      this.initAws();
      
      Queue<TreeNode> treeQueue = new LinkedList<TreeNode>();
      treeQueue.add(this.rootNode);
      TreeNode curNode = treeQueue.poll();
      
      System.out.println("Generating QuadTree...\n");

      while(curNode != null) {
         if (!curNode.isLeafNode()) {
            // Q1
            treeQueue.add(
               new TreeNode(
               (curNode.xHigh - curNode.xLow)/2 + curNode.xLow,
               (curNode.yHigh - curNode.yLow)/2 + curNode.yLow,
               curNode.xHigh,
               curNode.yHigh,
               curNode.binaryIndex + nodeCounterInBinary[0],
               (curNode.numberIndex * nodeCounterInBinary.length) + 1
               )
            );
            // Q2
            treeQueue.add(
               new TreeNode(
               curNode.xLow,
               (curNode.yHigh - curNode.yLow)/2 + curNode.yLow,
               (curNode.xHigh - curNode.xLow)/2  + curNode.xLow,
               curNode.yHigh,
               curNode.binaryIndex + nodeCounterInBinary[1],
               (curNode.numberIndex * nodeCounterInBinary.length) + 2
               )
            );
            // Q3
            treeQueue.add(
               new TreeNode(
               curNode.xLow,
               curNode.yLow,
               (curNode.xHigh - curNode.xLow)/2 + curNode.xLow,
               (curNode.yHigh - curNode.yLow)/2 + curNode.yLow,
               curNode.binaryIndex + nodeCounterInBinary[2],
               (curNode.numberIndex * nodeCounterInBinary.length) + 3
               )
            );
            // Q4
            treeQueue.add(
               new TreeNode(
               (curNode.xHigh - curNode.xLow)/2 + curNode.xLow,
               curNode.yLow,
               curNode.xHigh,
               (curNode.yHigh - curNode.yLow)/2 + curNode.yLow,
               curNode.binaryIndex + nodeCounterInBinary[3],
               (curNode.numberIndex * nodeCounterInBinary.length) + 4
               )
            );
         }
         else {
            String fileNode = this.buildNodeForFile(curNode.binaryIndex, curNode.xLow, curNode.yLow, curNode.xHigh, curNode.yHigh);
            this.leafCounter++;
            this.numberOfLeaves++;
          
            try{
               this.treeFileBuilder.bufferAdd(fileNode);
               
               if(this.leafCounter == this.partitionLimit || this.nodeCounter == this.numberOfNodes){
                  this.fileCounter++;
                  
                  File nodeFile = this.treeFileBuilder.buildFile(this.fileExtension, this.fileCounter);
                  this.treeFileBuilder.bufferClear();
                  
                  this.putFileToS3(nodeFile);
                  
                  if(this.fileCounter <= this.numberOfFiles){
                     this.leafCounter = 0;
                  }
               }
            }
            catch(IOException error){
               System.out.println(error);
            }          
         }

         this.nodeCounter++;
         curNode = treeQueue.poll();
      }
      
      this.displayResults();   
   }   
   
   public void displayResults() throws IOException{
   
     System.out.println("# Total Elements : " + this.nodeCounter);
     System.out.println("# Total Leaf Elements : " + this.numberOfLeaves);
     System.out.println("# Fetching data from bucket : " + this.bucketName + "\n");
     
     for(int i = 1; i < this.numberOfFiles - 1; i++){
       String key = this.filePrefix + i + "." + this.fileExtension;
       System.out.println("Key: " + key);
       S3Object object = s3.getObject(new GetObjectRequest(this.bucketName, key));

       this.displayTextInputStream(object.getObjectContent());
     }
   }
   
   private void initAws(){
     if(this.bucketName.equals("")){
       this.bucketName = "tf-quadtree-main-bucket";
     }
     this.credentials = null;
     
     try{
       this.credentials = new ProfileCredentialsProvider().getCredentials();
     }
     catch(Exception e){
       throw new AmazonClientException("Cannot load the credentials from the credential profiles file!\n", e);
     }
     
     this.s3 = new AmazonS3Client(this.credentials);
     this.awsRegion = Region.getRegion(Regions.US_WEST_2);
     
     this.s3.setRegion(this.awsRegion);
     
     boolean isBucketOnline = false;
     List<Bucket> buckets = this.s3.listBuckets();
     for(Bucket currentBucket : buckets){
       if(currentBucket.getName().equals(this.bucketName)){
         isBucketOnline = true;
       }
     }
     
     if(!isBucketOnline){
       this.s3.createBucket(this.bucketName);
       System.out.println("Creating new S3 bucket: " + this.bucketName);
     }
   }
   
   private void putFileToS3(File file){
     String key = this.filePrefix + this.fileCounter;
     
     System.out.println("Uploading " + key + "." + this.fileExtension + " to " + this.bucketName);
     this.s3.putObject(new PutObjectRequest(this.bucketName, key + "." + this.fileExtension, file));
   }
   
   private void setCounter(int count){
     this.nodeCounter = count;
     this.leafCounter = count;
   }
   
   private void displayTextInputStream(InputStream input) throws IOException { //https://github.com/aws/aws-sdk-java/blob/master/src/samples/AmazonS3/S3Sample.java
     BufferedReader reader = new BufferedReader(new InputStreamReader(input));
     while (true) {
       String line = reader.readLine();
       if (line == null) break;

       System.out.println("    " + line);
     }
     System.out.println();
   }
  
	private boolean isPowerTwo(int number) { return (number & (number - 1)) == 0; }

   private void checkForExit() {
      if(this.rootNode.xHigh > 0 && this.rootNode.yHigh > 0) {
         if(this.rootNode.xHigh == this.rootNode.yHigh) {
            if (this.isPowerTwo((int)this.rootNode.xHigh)) {
               if(!this.isPowerTwo((int)this.rootNode.yHigh)) {
                  System.out.println("Error : High-Y needs to be power of 2.");
                  System.exit(-1);
               }
               else{
                  this.numberOfNodes = this.getTotalNodes(this.rootNode.xHigh);
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
  
   private String buildNodeForFile(String index, double xMin, double yMin, double xMax, double yMax){
      return index + "," + xMin + "," + yMin + "," + xMax + "," + yMax;
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

   private class TreeNode {
      private double xLow, yLow, xHigh, yHigh;
      private String binaryIndex;
      private int numberIndex;

      private TreeNode(double xLow, double yLow, double xHigh, double yHigh, String binaryIndex, int numberIndex) {
         this.xLow = xLow;
         this.yLow = yLow;
         this.xHigh = xHigh;
         this.yHigh = yHigh;
         this.binaryIndex = binaryIndex;
         this.numberIndex = numberIndex;
      }
      
      @Override
      public String toString() {
         return String.format("Node : %d |$| (%.1f, %.1f, %.1f, %.1f) |$| Bin : %s\n", this.numberIndex, this.xLow, this.yLow, this.xHigh, this.yHigh, this.binaryIndex);
      }      

      private boolean isLeafNode() {
         return (this.xHigh - this.xLow) == 1 || (this.yHigh - this.yLow) == 1;
      }     
   }
}