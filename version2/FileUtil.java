/**
 * FileUtil File.
 * Composed with File Utility functions.
 */

package version2;

import java.lang.Math;
import java.io.Writer;
import java.io.StringWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileUtil {
   private String filePrefix;
   private Writer fileBuffer;

   public FileUtil(String prefix) {
      this.filePrefix = prefix;
      this.fileBuffer = new StringWriter();
   }
   
   public void bufferAdd(String input) throws IOException{
      try{
         this.fileBuffer.write(input + "\n");
      }
      catch(IOException error){
         System.out.println(error);
      }
   }
   
   public void bufferClear() throws IOException{
      try{
         this.fileBuffer.flush();
         this.fileBuffer.close();
         this.fileBuffer = new StringWriter();
      }
      catch(IOException error){
         System.out.println(error);
      }
   }
   
   public File buildFile(String fileExtension, int fileNumber) throws IOException{
      File outputFile = null;
      try{
         outputFile = new File(this.filePrefix + String.valueOf(fileNumber) + "." + fileExtension);
         outputFile.createNewFile();
         
         FileOutputStream outputStream = new FileOutputStream(outputFile);
         String input = this.fileBuffer.toString();
         outputStream.write(input.getBytes());
         
         return outputFile;
      }
      catch(IOException error){
         System.out.println(error);
      }
      
      return outputFile;
   }
  
   public int calculateNumberOfFiles(int numberOfNodes, int limit){
      return limit != 0 ? (int)Math.ceil((double)numberOfNodes/(double)limit) : 0;
   }
}