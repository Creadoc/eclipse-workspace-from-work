package gradientDescentPack;

import java.io.*;
import java.util.*;
import java.math.*;
import java.text.DecimalFormat;
import java.util.ArrayList; 

public class PS4 {
	
	ArrayList<Obj> trainObjects;
	ArrayList<Obj> testObjects;
	ArrayList<ArrayList<Double>> trainVals;
	ArrayList<ArrayList<Double>> testVals;
	ArrayList<Double> trainMeans;
	ArrayList<Double> testMeans;
	ArrayList<Double> trainSigmas;
	ArrayList<Double> testSigmas;
	ArrayList<Double> trainVars;
	ArrayList<Double> testVars;
	ArrayList<ArrayList<Double>> trainZs;
	ArrayList<ArrayList<Double>> testZs;
	ArrayList<Double> trainYs;
	ArrayList<Double> testYs;
	ArrayList<ArrayList<Double>> trainWeights;
	ArrayList<ArrayList<Double>> testWeights;
	double lineCount;
	int indexToRemove;

	public static void main(String[] args) {
		
		/* KEEEPING THIS HERE FOR READOUT CAPABILITIES!!!!
		 * 
		DecimalFormat df = new DecimalFormat("###.###");
		System.out.println(df.format(PI));
		 *
		*/
		
		
		PS4 ps = new PS4();
		ps.trainObjects = new ArrayList<Obj>();
		ps.testObjects = new ArrayList<Obj>();
		ps.trainVals = new ArrayList<ArrayList<Double>>();
		ps.testVals = new ArrayList<ArrayList<Double>>();
		ps.trainMeans = new ArrayList<Double>();
		ps.testMeans = new ArrayList<Double>();
		ps.trainSigmas = new ArrayList<Double>();
		ps.testSigmas = new ArrayList<Double>();
		ps.trainVars = new ArrayList<Double>();
		ps.testVars = new ArrayList<Double>();
		ps.trainZs = new ArrayList<ArrayList<Double>>();
		ps.testZs = new ArrayList<ArrayList<Double>>();
		ps.trainYs = new ArrayList<Double>();
		ps.testYs = new ArrayList<Double>();
		ps.trainWeights = new ArrayList<ArrayList<Double>>();
		ps.testWeights = new ArrayList<ArrayList<Double>>();
		ps.lineCount = 0.0;
//		ps.indexToRemove = Integer.parseInt(args[0])+1; 
		ps.indexToRemove = 17;
		ps.fileReader("dataset.txt");
//		System.out.println("train size: " + ps.trainObjects.size());
//		System.out.println("test size: " + ps.testObjects.size());
//		printObjListSize(ps.trainObjects);
//		printObjListSize(ps.testObjects);
		ps.process(ps.trainObjects, ps.trainVals, ps.trainMeans, ps.trainSigmas, ps.trainVars, ps.trainZs);
//		process(ps.testObjects, ps.testVals, ps.testMeans, ps.testSigmas, ps.testVars, ps.testZs);
//		printList(ps.trainZs);

		
//		System.out.println("here is train vals not including row of 1's but they are still in the vals dataset: ");
//		printList(ps.trainVals);
//		System.out.println("here is test vals not including row of 1's, but they are still in the vals dataset: ");
//		printList(ps.testVals);
		
		ps.initWeights(ps.trainWeights, ps.trainVals);
		ps.initWeights(ps.testWeights, ps.testVals);

		ps.gradientDescent(ps.trainZs, ps.trainYs, 0.001);
			
		
		
		
	// WE NEED TO GO BACK INTO THE OBJ CLASS AND REMOVE A BUNCH OF STUFF WE AREN'T GOING TO NEED BECAUSE
		// OF THE CHANGES WE DID TO THE WAY WE GOT ALL THE MEANS AND VARIANCES AND STANDARD DEVIATIONS.
		
//		ps.gradientDescent(0.0, 0.0, 0.0);
		
		
		
		
		
		
		
		
		
	}
	
	// x is an x variable
	// y is the corresponding y
	// a is the learning rate
	public void gradientDescent(ArrayList<ArrayList<Double>> x, ArrayList<Double> y, Double a) {
		double[] weigh = new double[x.get(0).size()];
		boolean converged = false;
		int index = 0;
		ArrayList <Double> lossList = new ArrayList<Double>();
		double loss = L(x, weigh, y);
		double cost = 0.0;
//		System.out.println("loss: " + loss);
		lossList.add(loss);
		while(!converged) {
//			for(int i = 0; i < weigh.length; i++) {
//			System.out.print("weigh: " + weigh[i] + ", ");
//			}
//			System.out.println();
			for(int k = 0; k < x.size(); k++) {
				//System.out.println("weigh k BEFORE: " + weigh[k]);
				weigh[k] -= a*LDerive(x, weigh, y, k);
				//System.out.println("weigh k: " + weigh[k]);
				//System.out.println("LDerive: " + LDerive(x, trainWeights, y, k));
			}
//			for(int i = 0; i < weigh.length; i++) {
//			System.out.print("new weigh!!!!!s: " + weigh[i] + ", ");
//			}
			index++;
			System.out.println("index: " + index);
			double current = L(x, weigh, y);
//			System.out.println("current: " + current);
			lossList.add(current);
//			System.out.println();
		
			//System.out.println("current: " + current + " loss: " + loss);
//			System.out.println("loss: " + loss + " current: " + current);
//			System.out.println("loss - current: " + (loss-current));
//			System.out.println(" (Math.abs(loss - current): " + Math.abs(loss - current));
//			System.out.println("Math.abs(loss - current)*100.0 " + Math.abs(loss - current)*100.0);
//			System.out.println("(Math.abs(loss - current)*100.0)/loss: " + (Math.abs(loss - current)*100.0)/loss);
			cost = (Math.abs((Double)loss - current)*100.0)/loss;
//			System.out.println("cost: " + cost + " a: " + a + " current: " + current + " loss " + loss);
			if(cost > a) {
				//System.out.println("loss List: " + lossList.toString());
				System.out.println("index: " + index);
				converged = true;
			}
			
		}
		
		
	}
	
	public Double L(ArrayList<ArrayList<Double>> vals, double[] weights, ArrayList<Double> ys) {
		ArrayList<Double> tempHold = new ArrayList<Double>();
		
//		for(int i = 0; i < weights.length; i++) {
//		System.out.print("THE WEIGHTS IN LOSS FUNCTION COMING IN: " + weights[i] + ", ");
//	}
		System.out.println();
		//THIS IS SUM OF (x_j * Beta_j)
		
		for(int i = 0; i < vals.size(); i++) {
			Double a = 0.0;
			for(int j = 0; j < vals.get(i).size(); j++) {
//				System.out.print(vals.get(i).get(j) + ", ");
				a += (vals.get(i).get(j) * weights[i]);
			}
//			System.out.println();
			tempHold.add(a);
			
		}
		System.out.println("tempHold size: " + tempHold.size());
		System.out.println("y's size: " + ys.size());
		ArrayList<Double> tempo = new ArrayList<Double>();
		Double theY = 0.0;
		
		
		//************************************************************************************* WORK ON THIS, IT ISN'T RIGHT
		//THIS IS Y - SUM OF (x_j * Beta_j)
		for(int i = 0; i < ys.size(); i++) {
			for(int j = 0; j < tempHold.size(); j++) {
				theY = Math.pow(ys.get(i) - tempHold.get(j), 2);
				tempo.add(theY);
			}
			
		}
		System.out.println(tempo.toString());
		Double sum = 0.0;
		
		//THIS IS SUM OF (Y - SUM OF (x_j * Beta_j)
		for(int i = 0; i < tempo.size(); i++) {
			//System.out.println(tempo.get(i));
			sum += tempo.get(i);
		}
		
		Double t = (double) (2*vals.get(0).size());
		Double fin = (1/t)*sum;
		System.out.println("in L: ");
		System.out.println(fin);
		return fin;
	}
	
	public Double LDerive(ArrayList<ArrayList<Double>> vals, double[] weights, ArrayList<Double> ys, int index) {
		Double a = 0.0;
		//ArrayList<Double> temp = new ArrayList<Double>();
		
//			for(int j = 0; j < vals.get(index).size(); j++) {
//				Double tempY = (Double)ys.get(j);
//				//System.out.println("y: " + tempY + "  x: " + vals.get(i).get(j) + " weights: " + weights.get(i).get(j) + " -x: " + -vals.get(i).get(j) + " answer: " + (tempY-vals.get(i).get(j)*weights.get(i).get(j))*-vals.get(i).get(j));
//				a += (tempY-vals.get(index).get(j)*weights[index])*-vals.get(index).get(j);
//			}
			
//		for(int i = 0; i < weights.length; i++) {
//			System.out.print("THE WEIGHTS IN LOSS Deriv: " + weights[i] + ", ");
//		}
		
		
			for(int i = 0; i < vals.size(); i++) {
				
				for(int j =0; j < vals.get(i).size(); j++) {
					Double tempY = (Double)ys.get(j);
//					System.out.print(weights[i] + ", ");
					a += (tempY-vals.get(i).get(j)*weights[i])*-vals.get(i).get(j);
				}
//				System.out.println();
			}
			//System.out.println("a added to : " + a);
			//temp.add(a);
			
		
		System.out.println("L Deriv: ");
		//System.out.println("temp size: " + temp.size() + ": " + temp.toString())
			System.out.println("a: " + a);
			return a;
	}
	

	
	
	//the process method also calculates the mean of the column:
	public void process(ArrayList<Obj> o, ArrayList<ArrayList<Double>> vals, ArrayList<Double> mus, ArrayList<Double> sigs, ArrayList<Double> vars, ArrayList<ArrayList<Double>> zs){
		//THIS INT S VARIABLE IS ASSUMING ALL ROWS HAVE EQUIVALENT COLUMN SIZES, WHICH IN THE TRAINING DATA, 
		//EACH ROW HAS 17 COLUMNS.
		//IF THIS WAS TO BE NOT THE CASE, WE WOULD INTRODUCE ANOTHER METHOD TO DO SOME CALCULATIONS BEFOREHAND, OR WE COULD
		//DO AN IF STATEMENT SOMETHING LIKE: {if(j < o.get(i).xs.size())-->process the values} INSIDE THE INNER FOR LOOP
		//OR SOMETHING SIMILAR TO THAT.
		int sizeOfMat = o.get(0).xs.size();
		Double total = 0.0;
		
		//calculating means
		for(int j = 0; j < sizeOfMat; j++) {
			vals.add(new ArrayList<Double>());
			
			for(int i = 0; i < o.size(); i++) {
				vals.get(j).add(o.get(i).xs.get(j));
				total += vals.get(j).get(i);
			}
			mus.add(total/vals.get(j).size());
			total = 0.0;
		}
		
		//calculating variance and sigmas
		Double sig = 0.0;
		Double sum = 0.0;
		for(int i = 0; i < vals.size(); i++) {
			for(int j = 0; j < vals.get(i).size(); j++) {
				sig = Math.pow(vals.get(i).get(j) - mus.get(i), 2);
				sum += sig;	
			}
			sum = sum/vals.get(i).size();
			vars.add(sum);
			sum = Math.sqrt(sum);
			sigs.add(sum);
			sum = 0.0;
		}
		
		//calculating z scores
//		System.out.println("vars.size(): " + vars.size() + " here is vars: " + vars.toString());
//		System.out.println("sigs.size(): " + sigs.size() + " here is sigs: " + sigs.toString());
		Double z = 0.0;
		//ArrayList<Double> tempz = new ArrayList<Double>();
		for(int i = 1; i < vals.size(); i++) {
			zs.add(new ArrayList<Double>());
			for(int j = 0; j < vals.get(i).size(); j++) {
				z = vals.get(i).get(j) - mus.get(i);
				z = z/sigs.get(i);
				if(Double.isNaN(z)) {
					z = 0.0;
				}
				zs.get(i-1).add(z);
			}
			
		}
		

//		for(int i = 0; i < zs.size(); i++) {
//			for(int j = 0; j < zs.get(i).size(); j++) {
//				System.out.print(" fresh z's: " + zs.get(i).get(j) + ", ");
//			}
//			System.out.println();
//		}
//		System.out.println();
	}
	
	public void fileReader(String filename) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(filename));
			String line;
			//String[] words;

			while ((line = br.readLine()) != null) {
				lineCount++;
			}

		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("There is a problem reading the file " + filename);
		}	
//		System.out.println("lineCount is: " + lineCount);
		double fractile1 = 0.8;
		double fractile2 = 0.2;
		fractile1 = Math.round(fractile1 * lineCount);
		fractile2 = Math.round(fractile2 * lineCount);
//		System.out.println("fractile1: " + fractile1);
//		System.out.println("fractile2: " + fractile2);

		try {
			BufferedReader br = new BufferedReader(new FileReader(filename));
			String line;
			String[] words;
			int count = 1;
			int count2 = 1;
			while ((line = br.readLine()) != null) {
				words = line.split(",");
				ArrayList<Double> values = new ArrayList<Double>();
				for(int i = 0; i < words.length; i++) {
					values.add(Double.parseDouble(words[i]));
				}
				Obj o = new Obj(values);
				o.xs.add(0, 1.0);
				o.y = values.get(indexToRemove);
				o.locationOfY = indexToRemove;
				//o.xs.remove(values.get(indexToRemove-1));
				if(count <= fractile1) {
					trainObjects.add(o);
					trainYs.add(o.y);
				}else {
					testObjects.add(o);
					testYs.add(o.y);
					count2++;
				}
				count++;
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("There is a problem reading the file " + filename);
		}
	}
	
	public void initWeights(ArrayList<ArrayList<Double>> weights, ArrayList<ArrayList<Double>> vals) {
		for(int i = 0; i < vals.size(); i++) {
			weights.add(new ArrayList<Double>());
			for(int j = 0; j < vals.get(i).size(); j++) {
				weights.get(i).add(0.0);
			}
		}
	}
	
	
	
	
//-------------------------------------------------------------------------------------------------------------------------
	
	public static void printObjListSize(ArrayList<Obj> list) {
		System.out.println("Object list size: " + list.size());
	}
	
	public static void printList(ArrayList<ArrayList<Double>> vals) {
		for(int i = 0; i < vals.size(); i++) {
			System.out.println("size of row: " + vals.get(i).size() + ": " + vals.get(i).toString());
			System.out.println("");
		}
	}
	
	
	public static void printListWithoutFirstElements(ArrayList<ArrayList<Double>> vals) {
		for(int i = 1; i < vals.size(); i++) {
			System.out.println(vals.get(i).toString());
			System.out.println("");
		}
	}
	
	public static void printYs(ArrayList<Obj> o) {
		System.out.println("The specified Y's: ");
		for(int i = 0; i < o.size(); i++) {
			System.out.print(o.get(i).y + ", and it's index: " + o.get(i).locationOfY + ".   ");
		}
		System.out.println();
	}
		
	public static void printObjList(ArrayList<Obj> o) {
		System.out.println("here is list: ");
		for(int a = 0; a < o.size(); a++) {
			System.out.println(o.get(a).xs.toString());
			
		}
	}
	
	public static void printIndividualObj(Obj o){
		for(int i = 0; i < o.xs.size(); i++) {
			System.out.print(o.xs.get(i) + ", ");
		}
	}
}
