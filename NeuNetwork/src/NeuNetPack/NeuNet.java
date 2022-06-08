package NeuNetPack;

/********************************
Name: Jimmy Collins
Username: ????
Problem Set: PS5
Due Date: April 18, 2021
********************************/


import java.util.*;
import java.io.*;

public class NeuNet {
	static ImageCnvrt ic;
	
	public static double[][] delta1 = new double[1][1];
	public static double[][] delta2 = new double[1][1];
	public static double[][] predictions = new double[1][1];
	public static double[][] output = new double[5][5];
	public static double[][] hidden = new double[5][5];
	public static double lambda = 3.0;
	public static double alpha = 0.25;
	public static double[][] w1data = new double[0][0];
	public static double[][] w2data = new double[0][0];
	public static double[][] ydata = new double[0][0];
	public static double[][] xdata = new double[0][0];
	public static double regular = 0.0;
	
	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		ic = new ImageCnvrt();
		xdata = fileCnvrtDoubleX("xdata.txt");
		w1data = fileCnvrtDoubleWeights("w1.txt");
		w2data = fileCnvrtDoubleWeights("w2.txt");
		ydata = fileCnvrtInteger("ydata.txt");
		int epoch = 700;
		double[] losses = new double[epoch];
		boolean converged = false;
		int i = 0;
		
		
		while(converged == false) {
			delta1 = new double[1][1];
			delta2 = new double[1][1];
			output = new double[1][1];
			predictions = new double[1][1];
			hidden = new double[1][1];
		forward(xdata);
		backward(xdata);
		if(i == epoch-1) {
			System.out.println("epoch: " + epoch);
		converged = true;
		printToFile(w1data, "weight1Output.txt");
		printToFile(w2data, "weight2Output.txt");
		arrayToFile(losses, "losses.txt");
		for(int a = 0; a < w2data.length; a++) {
			for(int j = 0; j < w2data[a].length; j++) {
				System.out.print(w2data[a][j] + " ");
			}
			System.out.println();
		}
		System.out.println();
		System.out.printf("Number of Input Records: %d \n",xdata.length);
		System.out.printf("Number of Features in Input Records: %d \n", xdata[0].length);
		System.out.printf("Iterations count: %d \n", i);
		}
		System.out.println("iteration number " + i);
		losses[i] = regular;
		i++;
		}
		long end = System.currentTimeMillis();
		float seco = (end - start)/1000F;
		float mins = seco/60F;
		System.out.println(mins + " minutes have passed for the program to finish.");
		System.out.println("END OF PROGRAM.");
	}
	
	public static void forward(double[][] xdata) {
		regular = 0;
		hidden = Matrix.multiply(xdata, Matrix.transpose(w1data));
		for (int i = 0; i < hidden.length; i++) {
			hidden[i] = activation(hidden[i]);
		}
		hidden = addBias(hidden);
		output = Matrix.multiply(hidden, Matrix.transpose(w2data));
		for (int i = 0; i < output.length; i++) {
			output[i] = activation(output[i]);
		}
		predictions = new double[output.length][output[0].length];
		double innerLoss = 0.0;
		double l = 0.0;
		for(int i =0; i < output.length; i++) {
			for(int j = 0; j < output[i].length; j++) {
					l = loss(xdata.length, ydata[i][j], output[i][j]);
					innerLoss += l;
			}
		}
		double summ = sumOfWeights(w1data, w2data);
		regular = ((1/xdata.length)*innerLoss) + ((lambda/2*xdata.length) * summ);
		//System.out.println("innerLoss: " + innerLoss);
		System.out.println("final loss product: " + regular);
		delta2 = calDelta2(delta2, predictions, ydata);
		delta1 = calDelta1(delta1, delta2, w2data, w1data, hidden, xdata);
	}
	
	
	// this might need tweaking...
	public static double[][] calDelta1(double[][] delta1, double[][] delta2, double[][] w2data, double[][] w1data, double[][] one, double[][] xdata){
		double[][] tempweight2 = new double[w2data.length][w2data[0].length];
		tempweight2 = createAndReturnCopyOfDoubleMatrix(w2data, tempweight2);
		for(int i = 0; i < tempweight2.length; i++) {
			tempweight2[i][0] = 0.0;
		}
		delta1 = new double[hidden.length][hidden[0].length];
		delta1 = Matrix.multiply(delta2, tempweight2);
		double[][] tempweight1 = new double[w1data.length][w1data[0].length];
		tempweight1 = createAndReturnCopyOfDoubleMatrix(w1data, tempweight1);
		for(int i = 0; i < tempweight1.length; i++) {
			tempweight1[i][0] = 0.0;
		}
		tempweight1 = Matrix.transpose(tempweight1);
		double[][] deriv = Matrix.multiply(xdata, tempweight1);
		for(int i = 0; i < deriv.length; i++) {
			deriv[i] = activation(deriv[i]);
		}
		deriv = addBias(deriv);
		for(int i = 0; i < deriv.length; i++) {
			for(int j = 0; j < deriv[i].length; j++) {
				deriv[i][j] = deriv[i][j]*(1.0 - deriv[i][j]);
			}
		}
		for(int i = 0; i < delta1.length; i++) {
			for(int j = 0; j < delta1[i].length; j++) {
				delta1[i][j] = delta1[i][j] * deriv[i][j];
			}
		}
		return delta1;
	}
	
	public static double[][] calDelta2(double [][] delta2, double[][] predictions, double[][] ydata){
		delta2 = new double[predictions.length][predictions[0].length];
		for(int i = 0; i < delta2.length; i++) {
			for(int j = 0; j < delta2[i].length; j++) {
				delta2[i][j] = output[i][j] - ydata[i][j];
			}
		}
		return delta2;
	}
	
	public static double sumOfWeights(double[][] matrix1, double[][] matrix2){
		double temp = 0.0;
		for(int i = 0; i < matrix1.length; i++) {
			for(int j = 0; j < matrix1[i].length;j++) {
				temp += Math.pow(matrix1[i][j], 2.0);
			}
		}
		for(int i = 0; i < matrix2.length; i++) {
			for(int j = 0; j < matrix2[i].length;j++) {
				temp += Math.pow(matrix2[i][j], 2.0);
			}
		}
		return temp;
	}
	
	public static double matrixSum(double[][] matrix) {
		double sum = 0.0;
		for(int i = 0; i < matrix.length; i++) {
			for(int j = 0; j < matrix[i].length; j++) {
				sum += matrix[i][j];
			}
		}
		return sum;
	}
	
	public static double loss(int recordsCount, double y, double yhat) {
		double loss = 0.0;
		loss = -y*(Math.log(yhat))-(1.0-y)*Math.log(1.0-yhat);
		return loss;
	}

	public static double[][] addBias(double[][] matrix) {
		double[][] temp = new double[matrix.length][matrix[0].length + 1];
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++) {
				temp[i][0] = 1.0;
				temp[i][j + 1] = matrix[i][j];
			}
		}
		return temp;
	}

	public static double[] activation(double[] arr) {
		double[] temp = new double[arr.length];
		for (int i = 0; i < arr.length; i++) {
			temp[i] = (1.0 / (1.0 + Math.exp(-arr[i])));
		}
		return temp;
	}
	
	public static void backward(double[][] xdata) {
		double[][] grad2 = gradient(delta2, hidden);
		double[][] grad1 = gradient(Matrix.dropFirstColumn(delta1), xdata);
		double[][] w1 = new double[w1data.length][w1data[0].length];
		double[][] w2 = new double[w2data.length][w2data[0].length];
		for(int i = 0; i < w1.length; i++) {
			w1[i][0] = 0.0;
			for(int j = 1; j < w1[i].length; j++) {
				w1[i][j] = w1data[i][j];
			}
		}
		for(int i = 0; i < w2.length; i++) {
			w2[i][0] = 0.0;
			for(int j = 1; j < w2[i].length; j++) {
				w2[i][j] = w2data[i][j];
			}
		}
		for(int i = 0; i < grad2.length; i++) {
			for(int j = 0; j < grad2[i].length; j++) {
				grad2[i][j] = grad2[i][j]/xdata.length + (lambda/xdata.length)*(w1[i][j]);
			}
		}
		for(int i = 0; i < grad1.length; i++) {
			for(int j = 0; j < grad1[i].length; j++) {
				grad1[i][j] = grad1[i][j]/xdata.length + (lambda/xdata.length)*(w1[i][j]);
			}
		}
		w1data = calculateWeights(w1data, grad1);
		w2data = calculateWeights(w2data, grad2);
		
	}
	
	public static double[][] calculateWeights(double weights[][], double[][] gradient){
		double[][] temp = new double[weights.length][weights[0].length];
		for(int i = 0; i < weights.length; i++) {
			for(int j = 0; j < weights[i].length; j++) {
				temp[i][j] = weights[i][j] - (alpha * gradient[i][j]);
			}
		}
		return temp;
	}
	
	public static double[][] gradient(double[][] delta, double[][] matrix){
		double[][] transpose = Matrix.transpose(delta);
		double[][] temp = Matrix.multiply(transpose, matrix);	
		return temp;
	}

	public static double[][] fileCnvrtDoubleX(String filename) {
		double temp[][];
		int count = 0;
		String[] words = new String[0];
		try {
			BufferedReader br = new BufferedReader(new FileReader(filename));
			String line;
			while ((line = br.readLine()) != null) {
				words = line.split(",");
				count++;
			}
			br.close();
		} catch (Exception e) {
			System.out.println("something happened in the file converter");
		}

		temp = new double[count][words.length + 1];
		String[][] wordy = new String[count][words.length];
		count = 0;
		try {
			BufferedReader br = new BufferedReader(new FileReader(filename));
			String line;
			while ((line = br.readLine()) != null) {
				words = line.split(",");
				wordy[count] = words;
				count++;
			}

			for (int i = 0; i < wordy.length; i++) {
				for (int j = 0; j < wordy[i].length; j++) {
					temp[i][0] = 1.0;
					temp[i][j + 1] = Double.valueOf(wordy[i][j]);
				}
			}
			br.close();
		} catch (Exception e) {
			System.out.println("something happened in the file converter, second time");
			e.printStackTrace();
		}
		System.out.print("file: " + filename + " ");
		System.out.println("end of x file reader.");
		return temp;
	}

	public static double[][] fileCnvrtDoubleWeights(String filename) {
		double temp[][];
		int count = 0;
		String[] words = new String[0];
		try {
			BufferedReader br = new BufferedReader(new FileReader(filename));
			String line;
			while ((line = br.readLine()) != null) {
				words = line.split(",");
				count++;
			}
			br.close();
		} catch (Exception e) {
			System.out.println("something happened in the file converter");
		}

		temp = new double[count][words.length];
		String[][] wordy = new String[count][words.length];
		count = 0;
		try {
			BufferedReader br = new BufferedReader(new FileReader(filename));
			String line;
			while ((line = br.readLine()) != null) {
				words = line.split(",");
				wordy[count] = words;
				count++;
			}

			for (int i = 0; i < wordy.length; i++) {
				for (int j = 0; j < wordy[i].length; j++) {
					temp[i][j] = Double.valueOf(wordy[i][j]);
				}
			}
			br.close();
		} catch (Exception e) {
			System.out.println("something happened in the file converter, second time");
			e.printStackTrace();
		}
		System.out.print("file: " + filename + " ");
		System.out.println("end of file reader");
		return temp;
	}
	
	public static double[][] fileCnvrtInteger(String filename) {
		double temp[][];
		int count = 0;
		String[] words = new String[0];
		try {
			BufferedReader br = new BufferedReader(new FileReader(filename));
			String line;
			while ((line = br.readLine()) != null) {
				words = line.split(",");
				count++;
			}
			br.close();
		} catch (Exception e) {
			System.out.println("something happened in the file converter");
		}

		temp = new double[count][words.length];
		String[][] wordy = new String[count][words.length];
		count = 0;
		try {
			BufferedReader br = new BufferedReader(new FileReader(filename));
			String line;
			while ((line = br.readLine()) != null) {
				words = line.split(",");
				wordy[count] = words;
				count++;
			}

			for (int i = 0; i < wordy.length; i++) {
				for (int j = 0; j < wordy[i].length; j++) {
					temp[i][j] = Double.valueOf(wordy[i][j]);
				}
			}
			br.close();
		} catch (Exception e) {
			System.out.println("something happened in the file converter, second time");
			e.printStackTrace();
		}
		System.out.print("file: " + filename + " ");
		System.out.println("end of integer (the y's) file reader.");
		double[][] finale = oneHot(temp);
		return finale;
	}
	
	public static double[][] oneHot(double[][] matrix){
		ArrayList<ArrayList<Double>> temp = new ArrayList<ArrayList<Double>>();
		ArrayList<Double> zero = new ArrayList<Double>();
		zero.add(1.0);zero.add(0.0);zero.add(0.0);zero.add(0.0);zero.add(0.0);zero.add(0.0);zero.add(0.0);zero.add(0.0);zero.add(0.0);zero.add(0.0);
		ArrayList<Double> one = new ArrayList<Double>();
		one.add(0.0);one.add(1.0);one.add(0.0);one.add(0.0);one.add(0.0);one.add(0.0);one.add(0.0);one.add(0.0);one.add(0.0);one.add(0.0);
		ArrayList<Double> two = new ArrayList<Double>();
		two.add(0.0);two.add(0.0);two.add(1.0);two.add(0.0);two.add(0.0);two.add(0.0);two.add(0.0);two.add(0.0);two.add(0.0);two.add(0.0);
		ArrayList<Double> three = new ArrayList<Double>();
		three.add(0.0);three.add(0.0);three.add(0.0);three.add(1.0);three.add(0.0);three.add(0.0);three.add(0.0);three.add(0.0);three.add(0.0);three.add(0.0);
		ArrayList<Double> four = new ArrayList<Double>();
		four.add(0.0);four.add(0.0);four.add(0.0);four.add(0.0);four.add(1.0);four.add(0.0);four.add(0.0);four.add(0.0);four.add(0.0);four.add(0.0);
		ArrayList<Double> five = new ArrayList<Double>();
		five.add(0.0);five.add(0.0);five.add(0.0);five.add(0.0);five.add(0.0);five.add(1.0);five.add(0.0);five.add(0.0);five.add(0.0);five.add(0.0);
		ArrayList<Double> six = new ArrayList<Double>();
		six.add(0.0);six.add(0.0);six.add(0.0);six.add(0.0);six.add(0.0);six.add(0.0);six.add(1.0);six.add(0.0);six.add(0.0);six.add(0.0);
		ArrayList<Double> seven = new ArrayList<Double>();
		seven.add(0.0);seven.add(0.0);seven.add(0.0);seven.add(0.0);seven.add(0.0);seven.add(0.0);seven.add(0.0);seven.add(1.0);seven.add(0.0);seven.add(0.0);
		ArrayList<Double> eight = new ArrayList<Double>();
		eight.add(0.0);eight.add(0.0);eight.add(0.0);eight.add(0.0);eight.add(0.0);eight.add(0.0);eight.add(0.0);eight.add(0.0);eight.add(1.0);eight.add(0.0);
		ArrayList<Double> nine = new ArrayList<Double>();
		nine.add(0.0);nine.add(0.0);nine.add(0.0);nine.add(0.0);nine.add(0.0);nine.add(0.0);nine.add(0.0);nine.add(0.0);nine.add(0.0);nine.add(1.0);
		
		
		for(int i = 0; i < matrix.length; i++) {
			for(int j = 0; j < matrix[i].length; j++) {
				if(matrix[i][j] == 10.0) {
					temp.add(zero);
				}
				if(matrix[i][j] == 1.0) {
					temp.add(one);
				}
				if(matrix[i][j] == 2.0) {
					temp.add(two);
				}
				if(matrix[i][j] == 3.0) {
					temp.add(three);
				}
				if(matrix[i][j] == 4.0) {
					temp.add(four);
				}
				if(matrix[i][j] == 5.0) {
					temp.add(five);
				}
				if(matrix[i][j] == 6.0) {
					temp.add(six);
				}
				if(matrix[i][j] == 7.0) {
					temp.add(seven);
				}
				if(matrix[i][j] == 8.0) {
					temp.add(eight);
				}
				if(matrix[i][j] == 9.0) {
					temp.add(nine);
				}
			}
		}
		double[][] doub = new double[temp.size()][temp.get(0).size()];
		for(int i = 0; i < temp.size(); i++) {
			for(int j = 0; j < temp.get(i).size(); j++) {
				doub[i][j] = (double)temp.get(i).get(j);
			}
		}
		System.out.println("end of oneHot.");
		return doub;
	}
	
	public static void printToFile(double[][] matrix, String filename) {
		try{
			PrintWriter writer;
			writer = new PrintWriter(filename);
			for (int i = 0; i < matrix.length; i++) {
				for(int j = 0; j < matrix[i].length; j++) {
					writer.printf("%.4f ", matrix[i][j]);
				}
				writer.println();
			}
			writer.close();
		}catch (FileNotFoundException e){
			System.out.println("Something went wrong in the printToFileMethod: " + e.getMessage());			
		}
	}
	
	public static void arrayToFile(double[] array, String filename) {
		try{
			PrintWriter writer;
			writer = new PrintWriter(filename);
			for (int i = 0; i < array.length; i++) {
					writer.printf("%.4f ", array[i]);
			}
			writer.close();
		}catch (FileNotFoundException e){
			System.out.println("Something went wrong in the printToFileMethod: " + e.getMessage());			
		}
	}
	
	public static double[][] createAndReturnCopyOfDoubleMatrix(double[][] a, double[][] b){
		//System.out.println("WARNING: Whatever matrix you placed as the second argument in this method is now created new and copying over the first arguments elements.");
		b = new double[a.length][a[0].length];
		for(int i = 0; i < a.length; i++) {
			for(int j = 0; j < a[i].length; j++) {
				b[i][j] = a[i][j];
			}
		}
		return b;
	}

}
