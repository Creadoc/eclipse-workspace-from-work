package uAMapGraphComputePack;

import java.io.*;
import java.util.*;

public class UAMapGraphCompute {

	File file;
	long[][] Graph;
	int c;
	String [][] Pi;
	PriorityQueue<Edge> pq;
	ArrayList<Node> nodes;
	PriorityQueue<Edge> temp;
	ArrayList<Edge> edgeList;
	
	public static void main(String[] args) {
		UAMapGraphCompute mgc = new UAMapGraphCompute();
		mgc.file = new File("The Map");
		mgc.nodes = new ArrayList<Node>();
		mgc.c = 0;
		mgc.pq = new PriorityQueue<Edge>();
		mgc.temp = new PriorityQueue<Edge>();
		mgc.edgeList = new ArrayList<Edge>();
		try {
			mgc.readFile(mgc.file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		mgc.finishGraph(mgc.Graph);
		mgc.finalStep(mgc.c, mgc.Graph);
		mgc.printGraph(mgc.Graph);
		
	}
	
	public void finalStep(int c, long[][]Graph) {
		for(int i = 0; i < c; i++) {
			extendShortestPath(Graph);
			}
	}
	
	public void dijkstra() {
		//look at your other program on your other computer.  You will need an adjacency list or graph of some kind.
	}
	
	public void finishGraph(long[][] Graph) {
		/*
		 * RULES: 
		 * in the initial run, we managed to create most the graph.
		 * 1. if there is a zero in the graph, it is now infinity.
		 * 
		 * 2.  if there is a node that loops back to itself, and
		 * it is not infinity (meaning it has a value), then we 
		 * will go ahead and keep that value it has now.  
		 * Otherwise it is a 0, meaning it has no path back
		 * to itself from itself (a loop)
		 */
		for(int i = 0; i < Graph.length; i++) {
			for(int j = 0; j < Graph[i].length; j++) {
				if(Graph[i][j] == 0) {
					Graph[i][j] = Integer.MAX_VALUE;
				}
				if(i == j) {
					if(Graph[i][j] != Integer.MAX_VALUE) {
						Graph[i][j] = Graph[i][j];
					}else{
						Graph[i][j] = 0;
					}
				}
			}
		}
	}
	
	public long min(long a, long b) {
		//System.out.println("comparing " + a + " with " + b);
		if(a > b) {
			return b;
		}else {
			return a;
		}
	}
	
	public long[][] extendShortestPath(long[][] Graph){
		long l[][] = new long[c][c];
		l = Graph;
		for(int i = 0; i < Graph.length; i++) {
			for(int j = 0; j < Graph.length; j++) {
				for(int k = 0; k < Graph.length; k++) {
					l[i][j] = min(l[i][j], l[i][k]+ Graph[k][j]);
				}
			}
		}
	
		return Graph;
	}
	
	public void readFile(File file) throws FileNotFoundException {
		int count = 0;
		int count2 = 0;
		try {
			BufferedReader br = new BufferedReader(new FileReader(file.getName()));
			String line;
			String[] tokens;
			while((line = br.readLine()) != null) {
				tokens = line.split("\t");
				count = Integer.parseInt(tokens[0]);
				count2 = Integer.parseInt(tokens[1]);
				if(count <= count2) {
					count = count2;
				}
				if(count > c) {
					c = count;
				}
			}
			br.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
		Graph = new long[c][c];
		Pi = new String[c][c];
		try {
			BufferedReader br = new BufferedReader(new FileReader(file.getName()));
			String line;
			String[] tokens;
			while((line = br.readLine()) != null) {
				tokens = line.split("\t");
				Edge e = new Edge(tokens[0], tokens[1], false);
				e.weight = Integer.parseInt(tokens[2]);
				pq.add(e);
			}
			br.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
		fillGraph(pq);
		createNodesList(c);
	}
	
	public ArrayList<Node> createNodesList(int c){
		for(int i = 0; i < c; i++) {
			Node n = new Node();
				n.val = (i+1);
				nodes.add(n);
		}
	
		for(int j = 0; j < edgeList.size(); j++) {
			Edge e = edgeList.get(j);
			for(int i = 0; i < nodes.size(); i++) {
					if(Integer.parseInt(e.start) == nodes.get(i).val) {
						nodes.get(i).e = e;
						nodes.get(i).dist.add(e.weight);
					}
					if(Integer.parseInt(e.end) == nodes.get(i).val) {
						// need to check this when I get back to the map I drew...
						// actually, this seems right
						nodes.get(i).pi.add(nodes.get(Integer.parseInt(e.start)));
					}
				}
			}
		printNodes(nodes);
		return nodes;
	}
	
	public void printNodes(ArrayList<Node> nodes) {
		for(int i = 0; i < nodes.size(); i++) {
			if(nodes.get(i).pi != null) {
			System.out.println(nodes.get(i).val + " " + nodes.get(i).dist + " pi: " + nodes.get(i).pi);
			}else {
				System.out.println(nodes.get(i).val + " " + nodes.get(i).dist + " no predecessor");
			}
		}
	}
	
	public void fillGraph(PriorityQueue<Edge> pq) {
		
		while(!pq.isEmpty()) {
			Edge e = pq.poll();
			edgeList.add(e);
			Graph[Integer.parseInt(e.start)-1][Integer.parseInt(e.end)-1] = e.weight;
			Graph[Integer.parseInt(e.end)-1][Integer.parseInt(e.start)-1] = e.weight;
			temp.add(e);
		}
		pq = temp;
	}
	
	public void printGraph(long[][] Graph) {
		for(int i = 0; i < Graph.length; i++) {
			System.out.printf(" %3d", i+1);
		}
		System.out.println("");
		for(int i = 0; i < Graph.length; i++) {
			System.out.printf("%d %s", i+1, "|");
			for(int j = 0; j < Graph[i].length; j++) {	
				if(Graph[i][j] != Integer.MAX_VALUE) {
					System.out.printf("%3d|", Graph[i][j]);
				}else {
						System.out.printf("%3s|", "INF");
					}
			}
			System.out.println("");
		}
	}
}
