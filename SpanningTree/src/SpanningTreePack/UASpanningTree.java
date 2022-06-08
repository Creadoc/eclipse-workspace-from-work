package SpanningTreePack;

import java.util.*;
import java.io.*;

public class UASpanningTree {
	
	File file;
	ArrayList<Edge> graph;
	int nodes;
	int edges;
	int desired;
	int count;
	boolean havePurple;
	ArrayList<Edge> tree;
	
	public static void main(String[] args) {
		UASpanningTree span = new UASpanningTree();
		span.nodes = 0;
		span.edges = 0;
		span.desired = 0;
		span.count = 0;
		span.havePurple = false;
		span.graph = new ArrayList<Edge>();
		span.tree = new ArrayList<Edge>();
		span.file = new File("Samples");
		span.readFile(span.file);
		System.out.println("beginning: ");
		span.stats();
		System.out.println("");
		System.out.println("");
		System.out.println("");
		span.prim(span.graph, span.graph.get(0));
		span.printTree(span.tree);
	}
	
	
	// I don't think we need these, but both will need work.
	public void prim(ArrayList<Edge> graph, Edge start) {
		Queue<Edge> queue = new PriorityQueue<Edge>();
		for(int i = 0; i < graph.size(); i++) {
			graph.get(i).dist = Integer.MAX_VALUE;
			graph.get(i).pi = null;
			graph.get(i).touched = false;
		}
		start.dist = 0;
		start.touched = true;
		for(int i = 0; i < graph.size(); i++) {
			queue.add(graph.get(i));
		}
		//System.out.println("root: " + queue.peek().start + " " + queue.peek().end);
		while(!queue.isEmpty()) {
			Edge u = queue.poll();
			System.out.println("u: " + defineColor(u) + " " + u.start + " " + u.end);
			u.touched = true;
			ArrayList<Edge> adj = adjacents(graph, u.start);
			for(int i = 0; i < adj.size(); i++) {
				if(adj.get(i).touched == false) {
					adj.get(i).pi = u;
					adj.get(i).dist = u.dist + 1;
					//graph.get(i).touched = true;
					System.out.println("in adj: " + defineColor(adj.get(i)) + " " + adj.get(i).start + " " + adj.get(i).end);
					tree.add(adj.get(i));
				}
			}
		}
	}
	
	public ArrayList<Edge> adjacents(ArrayList<Edge> graph, int location){
		ArrayList<Edge> adjacents = new ArrayList<Edge>();
		for(int i = 0; i < graph.size(); i++) {
			if(graph.get(i).start == location) {
				adjacents.add(graph.get(i));
			}
		}
		
		for(int i = 0; i < adjacents.size(); i++) {
			if(adjacents.get(i).color == true) {
				//System.out.println("we found: " + defineColor(adjacents.get(i)) + " " + adjacents.get(i).start + " " + adjacents.get(i).end);
				count++;
			}
			if(count >= desired && adjacents.get(i).color == true) {
				//adjacents.get(i).touched = true;
				System.out.println("removing: " + defineColor(adjacents.get(i)) + " " + adjacents.get(i).start + " " + adjacents.get(i).end);
				adjacents.remove(i);
			}
		}
		for(int i = 0; i < adjacents.size(); i++) {
			System.out.println("all adjacents: " + defineColor(adjacents.get(i)) + " " + adjacents.get(i).start + " " + adjacents.get(i).end);
		}
		return adjacents;
	}
	
	public void krusk() {
		
	}
	
	public void readFile(File file) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(file.getName()));
			String line;
			boolean firstRun = false;
			String[] tokens;
			while((line = br.readLine()) != null) {
				tokens = line.split(" ");
				if(firstRun == false) {
					nodes = Integer.parseInt(tokens[0]);
					edges = Integer.parseInt(tokens[1]);
					desired = Integer.parseInt(tokens[2]);
					firstRun = true;
				}
				if(firstRun = true) {
				boolean col;
				if(tokens[0].equals("O")) {
					col = false;
				}else {
					col = true;
				}
					graph.add(new Edge(Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2]), col));
					// assuming that the reverse edge is the same color, since that information was not supplied in the
					// problem set, I will just make the reverse edge the same color as the original edge.
					// Edge(1,2) is Purple, so therefore Edge(2,1) is also Purple.
					graph.add(new Edge(Integer.parseInt(tokens[2]), Integer.parseInt(tokens[1]), col));
				}
			}
			graph.remove(0);
			graph.remove(0);
			br.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void stats() {
		System.out.println("nodes: " + nodes + " edges: " + edges + " desired: " + desired);
		printGraph(graph);
		getAnswer();
	}
	
	public void printGraph(ArrayList<Edge> graph) {
		for(int i = 0; i < graph.size(); i++) {
			System.out.println(defineColor(graph.get(i)) + " " + graph.get(i).start + " " + graph.get(i).end);
		}
	}
	
	public void printTree(ArrayList<Edge> tree) {
		System.out.println("");
		System.out.println("in printTree");
		for(int i = 0; i < tree.size(); i++) {
			System.out.println(defineColor(tree.get(i)) + " " + tree.get(i).start + " " + tree.get(i).end);
		}
	}
	
	public String defineColor(Edge e) {
		if(e.color == false) {
			return "Orange";
		}else {
			return "Purple";
		}
	}
	
	public String getAnswer() {
		String answer = "Broken";
		System.out.println(answer);
		return answer;
	}

}
