package neuralNetworkpack;

import java.util.ArrayList;
import java.util.List;

public class UANeuron {
	private List<Double> inputWeights;
	private List<Double> outputWeights;
	double value = 0.0, min = -1; double max = 1, summation;
	
	public UANeuron() {
		
	}
	
	//using this as a temporary way to do an input layer basics for testing purposes.
	public UANeuron(double value) {
		this.value = value;
	}

	public List<Double> getInputWeights() {
		return inputWeights;
	}

	public void setInputWeights(List<Double> inputWeights, int size) {
		for(int i = 0; i < size; i++) {
			inputWeights.add(setRandomSingle());
		}
		inputWeights.set(0, 1.0);
		this.inputWeights = inputWeights;
	}

	public List<Double> getOutputWeights() {
		return outputWeights;
	}
	
	public void OutputWeights(List<Double> outputWeights, List<Double> targets, List<Double> values, int size) {
		for(int i = 0; i < size; i++) {
			outputWeights.add(targets.get(i) * values.get(i));
		}
		this.outputWeights = outputWeights;
	}
	
	public List<Double> setOutputWeights(List<Double> outputWeights, List<Double> targets, List<Double> values, int size) {
		for(int i = 0; i < size; i++) {
			outputWeights.add(targets.get(i) * values.get(i));
		}
		this.outputWeights = outputWeights;
		return this.outputWeights;
	}
	
	public Double setAnOutputWeight(Double target, Double value) {
		return target * value;
	}
	
	public double setRandomSingle() {
		return((Math.random() * (max - min)) + min);
	}
	
	public void sum(List<Double> outputWeights) {
		double summ = 0.0;
		for(int i = 0; i < outputWeights.size(); i++) {
			summ += outputWeights.get(i);
		}
		this.summation = summ;
	}
	
	public double returnSum(List<Double> outputWeights) {
		double summ = 0.0;
		for(int i = 0; i < outputWeights.size(); i++) {
			summ += outputWeights.get(i);
		}
		return summ;
	}
	
//	public String toString() {
//		return "inputs: " + this.inputWeights.toString() + " \noutputs: " + this.outputWeights.toString();
//	}
	
	
	// not needed for now, but optional.
	//------------------------------------------------------------------
	public void setInputWeightsByList(List<Double> inputWeights, int size) {
		inputWeights = setRandomList(inputWeights, size);
		this.inputWeights = inputWeights;
	}
	
	//could have a method for randomness with a range here like this:
	//this is filling the arraylist with small random negatives or positives AND adding a bias
	public List<Double> setRandomList(List<Double> inputWeights, int size) {
		
		for(int i = 0; i < size; i++) {
			inputWeights.add(((Math.random() * (max - min)) + min));
		}
		//inputWeights.set(0, 1.0);
		return inputWeights;
	}
	//------------------------------------------------------------------
}
