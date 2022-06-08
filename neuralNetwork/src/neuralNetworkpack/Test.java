package neuralNetworkpack;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;


public class Test {
	
	public static void main(String[] args) {
		
//		// either way here works now.
//		UANeuron nn = new UANeuron();
//		nn.setInputWeights(new ArrayList<Double>(), 4);
//		System.out.println(nn.getInputWeights());
//		UANeuron nn1 = new UANeuron();
//		nn1.setInputWeightsByList(new ArrayList<Double>(), 2);
//		System.out.println(nn1.getInputWeights());
		// either one of these above.
		List<Double> values = new ArrayList<Double>();
		ArrayList<UANeuron> neuronList = new ArrayList<UANeuron>();
		
		fileReader("somedata.txt", 0, neuronList, values);

		// so, I believe you've set the x's for the input layer
			// but I still think you need to set the weights.
		// then you can either then do the sum beforehand, or you can 
		// do the sum and then activation later, like in the "formHidden()" method you made.
		
		
		UAHiddenLayer hidden = new UAHiddenLayer();
		
		hidden.setNeuronsCountInLayer(2);
		formHidden(hidden, neuronList, values);
		System.out.println("END OF PROGRAM.");
	}
	
	public static void formHidden(UAHiddenLayer hidden, List<UANeuron> neuronList, List<Double> values) {
		
		List<Double> weights = new ArrayList<Double>();
		for(int i = 0; i< neuronList.size(); i++) {
			weights.add(neuronList.get(i).setRandomSingle());
		}
		System.out.print(weights.toString());
		System.out.println();
		//use this as a basis to make the sum thing and activation thing happen.... 
		// be mindful you're going to have to change your stuff up.
		for(int i = 0; i < hidden.getNeuronsCountInLayer(); i++) {
			List<Double> outputs = new ArrayList<Double>();
			for(int j = 0; j < neuronList.size(); j++) {
				outputs.add(neuronList.get(j).setAnOutputWeight(weights.get(j), values.get(j)));
			}
			System.out.println(outputs.toString());
			// also here you'll need to go ahead and find a way to save the activation output after you do the sum
			// and activation stuff.
			Double sum = 0.0;
			for(int k = 0; k < outputs.size(); k++) {
				sum += outputs.get(i);
			}
			System.out.println("sum: " + sum);
			// also remember, this happening for just ONE hidden layer.
			System.out.println();
		}
	}
	
	
	
	public static void fileReader(String filename, int lineCount, ArrayList<UANeuron> neuronList, List<Double> values) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(filename));
			String line;
			while ((line = br.readLine()) != null) {
				lineCount++;
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("There is a problem reading the file " + filename);
		}
		double fractile1 = 0.8;
		double fractile2 = 0.2;
		fractile1 = Math.round(fractile1 * lineCount);
		fractile2 = Math.round(fractile2 * lineCount);

		
		System.out.println("lineCount: " + lineCount);
		try {
			BufferedReader br = new BufferedReader(new FileReader(filename));
			String line;
			String[] words;
//			int count = 1;
//			int count2 = 1;
			while ((line = br.readLine()) != null) {
				words = line.split(",");
				
				
				for (int i = 0; i < words.length; i++) {
					values.add(Double.parseDouble(words[i]));
				}
				values.add(0, 1.0);
				
				for(int i = 0; i < values.size()-1; i++) {
					UANeuron nn = new UANeuron(values.get(i));
					neuronList.add(nn);
				}
				
				
				//keep in mind this may be done wrong because this is going left to right by rows, not up and down by columns.
				// if that needs to be the case, then we need to make another transpose method in order to accrue those values appropriately.
				// but to do that, we will probably need to just put these values in a data structure and pass that to a method to make a transpose of 
				// that data structure
				

//				if (count <= fractile1) {
//					
//				} else {
//					
//				}
//				count++;
			}
			UAInputLayer in = new UAInputLayer();
			in.init(neuronList);
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("There is a problem reading the file " + filename);
		}
	}

}
