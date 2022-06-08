package gradientDescentPack;

import java.util.ArrayList;
import java.util.Arrays;

public class Obj {
	ArrayList<Double> xs = new ArrayList<Double>();
	int locationOfY;
	Double y;
	Double mean;
	ArrayList<Double> residuals = new ArrayList<Double>();
	
	
	public Obj(ArrayList<Double> xs, int locationOfY, Double y){
		this.xs = xs;
		this.locationOfY = locationOfY;
		this.y = y;
		mean = calMean(xs);
		residuals = residuals();
	}
	
	public Obj(ArrayList<Double> xs){
		this.xs = xs;

	}
	
	public Obj() {
		
	}
	
	public double calMean(ArrayList<Double> xs) {
		double sum = 0.0;
		int total = 0;
		for(int i = 0; i < xs.size(); i++) {
			sum += xs.get(i);
			total++;
		}
		return sum/total;
	}
	
	public ArrayList<Double> residuals() {
		ArrayList<Double> resids = new ArrayList<Double>();
		double mean = calMean(this.xs);
		Double temp = 0.0;
		for(int i = 0; i < xs.size(); i++) {
			temp = xs.get(i) - mean;
			resids.add(temp);
		}
		temp = 0.0;
		for(int i = 0; i < resids.size(); i++) {
			temp+= resids.get(i);
		}
		if(temp < 0.0001 && temp > -0.0001) {
			//System.out.println("sum = " + temp + ", but because it was so minimal we are making it zero.");
			temp = 0.0;
			return resids;
		}else {
			//System.out.println("null");
			return null;
		}
		
	}
	
}
