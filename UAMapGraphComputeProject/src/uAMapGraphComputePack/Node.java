package uAMapGraphComputePack;

import java.util.ArrayList;

public class Node implements Comparable<Node>{
	
	int val;
	ArrayList<Integer> dist = new ArrayList<Integer>();
	ArrayList<Node> pi = new ArrayList<Node>();
	Edge e;
	boolean touched;
	
	public Node(int val, ArrayList<Integer> dist, ArrayList<Node> pi) {
		this.val = val;
		this.dist = dist;
		this.pi = pi;
		getEdge();
	}
	
	public Node() {
		
	}
	
	public Node(int val) {
		this.val = val;
	}
	
	public Edge getEdge() {
		if(Integer.parseInt(e.start) == this.val) {
			return e;
		}else {
			System.out.println("no edge found for node " + this.val);
			return null;
		}
	}

	@Override
	public int compareTo(Node a) {
		if(val > a.val) {
			return -1;
		}else if(val > a.val) {
			return 1;
		}
		return 0;
	}
}
