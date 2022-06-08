package NeuNetPack;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.util.*;
import java.io.*;
import javax.imageio.ImageIO;

public class ImageCnvrt {

   Image  image;
   ArrayList<ArrayList<Integer>> featureMatrixTranspose;
   ArrayList<Integer> featList;
   ArrayList<ArrayList<Integer>> featureMatrix;
   
   public ImageCnvrt() {
	   
   }
   
   public ImageCnvrt(String filename) {
   featureMatrixTranspose = new ArrayList<ArrayList<Integer>>();
   int w = 0;
   int h = 0;
   featList = new ArrayList<Integer>();
      try {
         File input = new File(filename);
         image = ImageIO.read(input);
         BufferedImage img = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_BYTE_GRAY);
         Graphics g = img.createGraphics();
         g.drawImage(image, 0, 0, null);
         g.dispose();
         w = img.getWidth();
         h = img.getHeight();
         Raster raster = img.getData();
         for (int j = 0; j < w; j++) {
        	 featureMatrixTranspose.add(new ArrayList<Integer>());
             for (int k = 0; k < h; k++) {
                 featureMatrixTranspose.get(j).add((raster.getSample(j, k, 0)));
             }
         }
         //don't really need this, but eh.
         File ouptut = new File("grayscaleImg.jpg");
         ImageIO.write(img, "jpg", ouptut);
         
         featureMatrix = reverseTranspose(featureMatrixTranspose, featList);
         //matrixPrinter(featureMatrix);
         
      } catch (Exception e) {
    	  System.out.println("Something went wrong with the file grab");
      }
      //System.out.println(featList.toString());
      System.out.println("Image convert done.  Check directory for file named \"grayscale.jpg\".");
   }
   
   public void matrixPrinterInteger(ArrayList<ArrayList<Integer>> matrix) {
       for(int i = 0; i < matrix.size(); i++) {
      	 for(int j = 0; j < matrix.get(i).size(); j++) {
      		 System.out.printf("%3d " , matrix.get(i).get(j));
      	 }
      	 System.out.println("");
       }
   }
   
   public void matrixPrinterDouble(ArrayList<ArrayList<Double>> matrix) {
       
	   for(int i = 0; i < matrix.size(); i++) {
		   int count = 0;
      	 for(int j = 0; j < matrix.get(i).size(); j++) {
      		 
      		 if(count < Math.ceil(Math.sqrt(matrix.get(i).size())-1)) {
      			// String a = String.valueOf(matrix.get(i).get(j));
     			// System.out.print(a.substring(0, 3) + "");
      			System.out.printf("%.3f" , matrix.get(i).get(j));
      			count++;
      		 }
      		 else {
      			// String a = String.valueOf(matrix.get(i).get(j));
      			// System.out.println(a.substring(0, 3) + "");
      			System.out.printf("%.3f\n" , matrix.get(i).get(j));
      			//System.out.println();
      			 count = 0;
      		 }
      		 
      		 //System.out.print("count; " + count + " ");
      		 
      	 }
      	 System.out.println("");
      	System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
       }
   }
   
   public void printListAsMatrix(ArrayList<Double> list) {
	   int count = 0;
	   for(int j = 0; j < list.size(); j++) {
    		 if(count < 27) {
    			 System.out.printf("%.3f" , list.get(j));
    			 count++;
    		 }else {
    			 System.out.printf("%.3f\n" , list.get(j));
    			 count = 0;
    		 }
	   }
   }
   
   public void listPrinter(ArrayList<Integer> list) {
	   System.out.println(list.toString());
   }
   
   public static ArrayList<ArrayList<Integer>> reverseTranspose(ArrayList<ArrayList<Integer>> mat, ArrayList<Integer> list){
	    int m = mat.size();
	    int n = mat.get(0).size();
	    ArrayList<ArrayList<Integer>> transposed = new ArrayList<ArrayList<Integer>>();
	    for(int x = 0; x < n; x++) {
	    	transposed.add(new ArrayList<Integer>());
	        for(int y = 0; y < m; y++) {
	            transposed.get(x).add(mat.get(y).get(x));
	            list.add(transposed.get(x).get(y));
	        }
	    }

	    return transposed;
	}
   
   public ArrayList<ArrayList<Integer>> getFeatureMatrixTranspose(){
	   return featureMatrixTranspose;
   }
   
   public ArrayList<Integer> getFeatureList(){
	   return featList;
   }
   
   public ArrayList<ArrayList<Integer>> getFeatureMatrix(){
	   return featureMatrix;
   }
   
//   //DON'T NEED BUT HERE FOR TESTING... REMOVE AND ADD TO MAIN CLASS AFTER TESTS ARE COMPLETE
//   static public void main(String args[]) throws Exception {
//      ImageCnvrt obj = new ImageCnvrt();
//   }
}
