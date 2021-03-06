package ps6Pack;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;
import javax.swing.Timer;

class GameAgent {

	public static Node[][]graph = new Node[0][0];
	public static PriorityQueue<Node> reached = new PriorityQueue<Node>();
	public static ArrayList<Node> theTrail = new ArrayList<Node>();
	
	void drawPlan(Graphics g, GameModel m) {
		g.setColor(Color.red);
		g.drawLine((int)m.getX(), (int)m.getY(), (int)m.getDestXValue(), (int)m.getDestYValue());
		
	}
	
	void drawTempPlan(Graphics g, GameModel m, Node current) {
		g.setColor(Color.red);
		g.drawLine(current.x, current.y, (int)m.getDestXValue(), (int)m.getDestYValue());
		
	}
	
	void drawCircleGreen(Graphics g, GameModel m, int x, int y) {
		g.setColor(Color.green);
		g.drawOval(x+3, y+20, 10, 10);
	}
	
	void drawCircleRed(Graphics g, GameModel m, int x, int y) {
		g.setColor(Color.red);
		g.drawOval(x+2, y+20, 10, 10);
	}
	
	void drawCircleYellow(Graphics g, GameModel m, int x, int y) {
		g.setColor(Color.yellow);
		g.drawOval(x+2, y+20, 10, 10);
	}
	
	void drawCircleBlue(Graphics g, GameModel m, int x, int y) {
		g.setColor(Color.blue);
		g.drawOval(x+3, y+20, 10, 10);
	}

	void update(GameModel m)
	{
		GameController c = m.getController();
		while(true)
		{
			MouseEvent e = c.nextMouseEvent();
			if(e == null)
				break;
			//m.setDest(e.getX(), e.getY());
			Node start = new Node(Math.round((int)m.getX()/10)*10, Math.round((int)m.getY()/10)*10, 0.0f);
			Node dest = new Node(Math.round((int)e.getX()/10)*10, Math.round((int)e.getY()/10)*10, m.getSpeedOfTravel(e.getX(), e.getY()));
			
			if(e.getButton() == e.BUTTON1) {
			Node best = bestFirst(m, graph, start, c, dest);
			}
			
			if(e.getButton() == e.BUTTON3) {
			Node astar = aStar(m, graph, start, c, dest);
			}
		}
	}
	
	public Node bestFirst(GameModel m, Node[][]graph, Node start, GameController c, Node dest){

		PriorityQueue<Node> frontier = new PriorityQueue<Node>();
		frontier.add(start);
		reached = new PriorityQueue<Node>();
		reached.add(start);
		while(!frontier.isEmpty()) {
			Node node = frontier.peek();
		if(dest.x == node.x && dest.y == node.y) {
			this.drawCircleRed(c.view.getGraphics(), m, dest.x, dest.y);
			System.out.println("we found it?");
			System.out.println("dest: " + dest.toString());
			System.out.println("");
			//m.setDest(dest.x, dest.y);
			initializeGraph(c);
			return node;
		}
		ArrayList<Node> children = expand(graph, node, m);
			if(children != null) {
				for(int i = 0; i < children.size(); i++) {
						Node s = children.get(i);
						s.touched = true;
						float totalWeight = s.speed + node.speed;
						if((s.x > m.XMAXIMUM || s.x < 0) || (s.y > m.YMAXIMUM || s.y < 0)) {
							frontier.remove(s);
							reached.remove(s);
							break;
						}
						if((!reached.contains(s) || !frontier.contains(s)) && graph[s.x][s.y].touched == false) {
							s.touched = true;
							graph[s.x][s.y].touched = true;
							s.parent = node;
							this.drawCircleGreen(c.view.getGraphics(), m, s.x, s.y);
							frontier.add(s);
						}else {
							if(totalWeight > s.speed && graph[s.x][s.y].traveled == false) {
								s.parent = node;
								s.speed = totalWeight;
								this.drawCircleBlue(c.view.getGraphics(), m, s.x, s.y);
								s.traveled = true;
								graph[s.x][s.y].traveled = true;
								theTrail.add(s);
							}
						}
				}
			}
			frontier.remove(node);
			reached.add(node);
			try {
				Thread.currentThread().sleep(2);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public Node aStar(GameModel m, Node[][]graph, Node start, GameController c, Node dest){

		PriorityQueue<Node> frontier = new PriorityQueue<Node>();
		frontier.add(start);
		reached = new PriorityQueue<Node>();
		start.calculateEuclideanDistance(start, dest);
		start.f = start.g + start.calculateHeuristic(dest);
		reached.add(start);
		while(!frontier.isEmpty()) {
			Node node = frontier.peek();
		if(dest.x == node.x && dest.y == node.y) {
			this.drawCircleRed(c.view.getGraphics(), m, dest.x, dest.y);
			//m.setDest(dest.x, dest.y);
			System.out.println("reached size: " + reached.size());
			initializeGraph(c);
			return node;
		}
		float distance = node.calculateEuclideanDistance(node, dest);
		ArrayList<Node> children = expand(graph, node, m);
			if(children != null) {
				for(int i = 0; i < children.size(); i++) {
						Node s = children.get(i);
						s.touched = true;
						float totalWeight = node.g + s.speed;
						float sDistance = s.calculateEuclideanDistance(s, dest);
						if((s.x > m.XMAXIMUM || s.x < 0) || (s.y > m.YMAXIMUM || s.y < 0)) {
							frontier.remove(s);
							reached.remove(s);
							break;
						}
						if((!reached.contains(s) || !frontier.contains(s)) && (graph[s.x][s.y].touched == false) && (distance >= sDistance)) {
							s.touched = true;
							graph[s.x][s.y].touched = true;
							s.parent = node;
							graph[s.x][s.y].parent = graph[node.x][node.y];
							s.g = totalWeight;
							graph[s.x][s.y].calculateEuclideanDistance(graph[s.x][s.y], graph[dest.x][dest.y]);
							graph[s.x][s.y].g = totalWeight;
							s.f = s.g + node.calculateHeuristic(dest);
							this.drawCircleGreen(c.view.getGraphics(), m, s.x, s.y);
							frontier.add(s);
						}else {
							if(totalWeight > s.g && graph[s.x][s.y].traveled == false) {
								s.parent = node;
								graph[s.x][s.y].parent = graph[node.x][node.y];
								s.calculateEuclideanDistance(s, dest);
								graph[s.x][s.y].calculateEuclideanDistance(graph[s.x][s.y], graph[dest.x][dest.y]);
								s.g = totalWeight;
								graph[s.x][s.y].g = totalWeight;
								this.drawCircleBlue(c.view.getGraphics(), m, s.x, s.y);
								s.traveled = true;
								graph[s.x][s.y].traveled = true;
								s.f = s.g + s.calculateHeuristic(dest);
								theTrail.add(s);
								if(reached.contains(s) || reached.contains(node) || reached.contains(graph[s.x][s.y])) {
									reached.remove(s);
									frontier.add(s);
									this.drawCircleRed(c.view.getGraphics(), m, s.x, s.y);
								}
							}
						}
				}
			}
			frontier.remove(node);
			reached.add(node);
			try {
				Thread.currentThread().sleep(2);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}
	
	//should this be static?
	public ArrayList<Node> expand(Node[][] graph, Node node, GameModel m) {
		if((node.x > m.XMAXIMUM-10 || node.x < 0+10) || (node.y > m.YMAXIMUM-10 || node.y < 0+10)) {
			return null;
		}
		Node[][] temp = new Node[3][3];
		temp[0][0] = new Node(node.x-10, node.y+10, m.getSpeedOfTravel(node.x-10, node.y+10));
		temp[0][1] = new Node(node.x, node.y+10, m.getSpeedOfTravel(node.x, node.y+10));
		temp[0][2] = new Node(node.x+10, node.y+10, m.getSpeedOfTravel(node.x+10, node.y+10));
		temp[1][0] = new Node(node.x-10, node.y, m.getSpeedOfTravel(node.x-10, node.y));
		temp[1][1] = new Node(node.y, node.x, m.getSpeedOfTravel(node.x, node.y));
		temp[1][2] = new Node(node.x+10, node.y, m.getSpeedOfTravel(node.x+10, node.y));
		temp[2][0] = new Node(node.x-10, node.y-10, m.getSpeedOfTravel(node.x-10, node.y-10));
		temp[2][1] = new Node(node.x, node.y-10, m.getSpeedOfTravel(node.x, node.y+10));
		temp[2][2] = new Node(node.x+10, node.y-10, m.getSpeedOfTravel(node.x+10, node.y-10));		
		
		ArrayList<Node> children = new ArrayList<Node>();
		for(int i = 0; i < temp.length; i++) {
			for(int j =0; j < temp[i].length; j++) {
				children.add(temp[i][j]);
			}
		}
		children.remove(4);
		return children;
	}

	public static void main(String[] args) throws Exception
	{
		GameController c = new GameController();
		c.initialize();
		initializeGraph(c);
		//printToFile(graph, "testmap.txt");
		// This will instantiate a new instance of JFrame.  Each will spawn in another thread to generate events
		//and keep the entire program running until the JFrame is terminated.
		c.view = new GameView(c, c.model);
		
		System.out.println("theTrail out there: " + theTrail.size());
		for(int i = 0; i < theTrail.size(); i++) {
			System.out.println(theTrail.get(i).speed + " Hello?");
			c.getModel().setDest(theTrail.get(i).x, theTrail.get(i).y);
		}
		
		
		// this will create an ActionEvent at fairly regular intervals.   Each of the events are handled by
		// GameView.actionPerformed()
		new Timer(20, c.view).start(); 
	}
	
	public static void initializeGraph(GameController c) {
		graph = new Node[(int)Math.round(c.getModel().XMAXIMUM)][(int)Math.round(c.getModel().YMAXIMUM)];
		System.out.println("graph size: " + graph.length + " " + graph[0].length);
		for(int i = 0; i < graph.length; i++) {
			for(int j = 0; j < graph[i].length; j++) {
				graph[i][j] = new Node(i, j, c.getModel().getSpeedOfTravel(i, j));
				graph[i][j].touched = false;
				graph[i][j].traveled = false;
			}
		}
		graph[(int)c.getModel().getY()][(int)c.getModel().getX()].speed = 0.0f;
		System.out.println("should be new graph");
	}

	public static void printToFile(float[][] matrix, String filename) {
		try{
			PrintWriter writer;
			writer = new PrintWriter(filename);
			for (int i = 0; i < matrix.length; i++) {
				for(int j = 0; j < matrix[i].length; j++) {
					writer.printf("%.0f", matrix[i][j]);
				}
				if(i < matrix.length-1) {
					writer.println();
				}
			}
			writer.close();
		}catch (FileNotFoundException e){
			System.out.println("Something went wrong in the printToFileMethod: " + e.getMessage());			
		}
	}
}


