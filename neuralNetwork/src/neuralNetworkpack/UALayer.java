package neuralNetworkpack;

import java.util.*;

public class UALayer {

	private List<UANeuron> neurons;
	private int neuronsCountInLayer;
	
	
	public List<UANeuron> getNeurons() {
		return neurons;
	}

	
	public void setNeurons(List<UANeuron> neurons) {
		this.neurons = neurons;
	}
	
	public int getNeuronsCountInLayer() {
		return neuronsCountInLayer;
	}
	
	public void setNeuronsCountInLayer(int neuronsCountInLayer) {
		this.neuronsCountInLayer = neuronsCountInLayer;
	}
	
}


class UAInputLayer extends UALayer{
	public ArrayList<UANeuron> init(ArrayList<UANeuron> layer) {
		return layer;
		// might need to watch closely at what this is doing in another class.
	}
	
	public void print(UAInputLayer layer) {
		layer.toString();
	}
}

class UAOutputLayer extends UALayer{
	public UAOutputLayer init(UAOutputLayer layer) {
		return layer;
		// might need to watch closely at what this is doing in another class.	
	}
	
	public void print(UAOutputLayer layer) {
		layer.toString();
	}
}

class UAHiddenLayer extends UALayer{
	public UAHiddenLayer init(UAHiddenLayer h, List<UAHiddenLayer> list, UAInputLayer i, UAOutputLayer o) {
		return h;
		// might need to watch closely at what this is doing in another class.	
	}
	
	public void print(UAHiddenLayer layer) {
		layer.toString();
	}
}
