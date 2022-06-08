package SpanningTreePack;

public class Edge implements Comparable<Edge>{
	int start;
	int end;
	boolean color; // false = ORANGE, true = PURPLE
	boolean touched; //false = not been there, true = been there;
	int dist; // dist is key for now.
	Edge pi;
	int fin;
	
	public Edge(int start, int end, boolean color) {
		this.start = start;
		this.end = end;
		this.color = color;
	}
		
	public Edge() {
			
	}

	@Override
	public int compareTo(Edge ed) {
		if(dist < ed.dist)
			return -1;
		else if(dist > ed.dist)
			return 0;
		return 0;
	}

}
