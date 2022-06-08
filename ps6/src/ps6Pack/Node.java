package ps6Pack;


public class Node implements Comparable<Node>{
	 int x;
	 int y;
	 float speed;
	 Node parent;
	 boolean traveled = false;
	 boolean touched = false;
	 float heuristic;
	 float distance;
	 float f;
	 float g;
	 
//	 public Node() {
//		 
//	 }

	public Node(int x, int y, float speed) {
		 this.x = x; 
		 this.y = y;
		 this.speed = speed;
	 }
	
	public float calculateHeuristic(Node other) {
		float heuristic = (speed + other.speed) + (distance + other.distance);
		return heuristic;
	}
	
	public void setHeuristic(float number) {
		this.heuristic = number;
	}
	
	public float calculateEuclideanDistance(Node self, Node next) {
		double deltaX = Math.pow(self.x - next.x, 2);
		double deltaY = Math.pow(self.y - next.y, 2);
		float dist = (float)Math.sqrt(deltaX + deltaY);
		setDistance(dist);
		return dist;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Float.floatToIntBits(speed);
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}
	
	 public Node getParent() {
		return parent;
	}
	 
	public void setDistance(float distance) {
		this.distance = distance;
	}
	
	public float getDistance() {
		return distance;
	}

	public void setParent(Node parent) {
		this.parent = parent;
	}
	
	public String toString() {
		String t = "x: " + this.x + " y: " + this.y + " speed: " + this.speed;
		//System.out.println(t);
		return t;
	}

	@Override
	public int compareTo(Node o) {
		return Float.compare(o.speed, speed);
//		if (speed < o.speed) {
//			return 1;
//		}
//		if(speed == o.speed) {
//			return 0;
//		}
//		else {
//			return -1;
//		}
	}
	 
	

}
