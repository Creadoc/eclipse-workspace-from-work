package NeuNetPack;

import java.util.*;
import java.io.*;

public class featuresFileCreator {
	public ArrayList<Integer> features;
	
	public int getRandomNumber(int min, int max) {
		return (int) ((Math.random() * (max - min)) + min);
	}
	
	public featuresFileCreator(int size, int min, int max) {
		features = new ArrayList<Integer>();
		for(int i = 0; i < size; i++) {
			features.add(getRandomNumber(min, max));
		}
	}
	
	public ArrayList<Integer> getFeatures(ArrayList<Double> features) {
		return this.features;
	}
	
//	public static void main(String[] args) {
//		featuresFileCreator fs = new featuresFileCreator(784, 0, 255);
//		fs.writeToFile(fs.features);
//		
//		try{
//		    FileInputStream readData = new FileInputStream("sometest.txt");
//		    ObjectInputStream readStream = new ObjectInputStream(readData);
//
//		    ArrayList<Integer> feats = (ArrayList<Integer>) readStream.readObject();
//		    readStream.close();
//		    System.out.println("Here they are!!!: " + feats.toString());
//		    System.out.println("Size: " + feats.size());
//		}catch (Exception e) {
//		    e.printStackTrace();
//		}
//	}
	
	public void writeToFile(ArrayList<Integer> features) {
		try {
			 FileOutputStream writeData = new FileOutputStream("sometest.txt");
			    ObjectOutputStream writeStream = new ObjectOutputStream(writeData);

			    writeStream.writeObject(features);
			    writeStream.flush();
			    writeStream.close();
		}catch(Exception e) {
			System.out.println("something went wrong in the fileWriter method");
		}
	}

}
