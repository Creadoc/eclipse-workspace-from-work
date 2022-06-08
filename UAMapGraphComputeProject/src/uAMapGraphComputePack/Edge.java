package uAMapGraphComputePack;

import java.util.Comparator;

public class Edge implements Comparable<Edge>{
	String start;
	String end;
	int weight;
	boolean touched;
	
	public Edge(String start, String end, boolean touched) {
		this.start = start;
		this.end = end;
		this.touched = touched;
	}

	@Override
	public int compareTo(Edge o) {
		return COMPARATOR.compare(this, o);
	}
	
	protected int getWeight() {
		return weight;
	}
	
	protected int getStart() {
		return Integer.parseInt(start);
	}
	
	private static final Comparator<Edge> 
	COMPARATOR = Comparator.comparingInt(Edge::getStart).thenComparing(Edge::getWeight);
}
