package UAFindMyClassPack;

import java.util.*;
import java.io.*;

public class UAFindMyClass {

	static File maze;
	int openSpace;
	int closedSpace;
	int rows;
	int cols;
	char[] row;
	char[][] graph;
	int linecount;
	int charCount;
	boolean foundStart;
	boolean foundFinish;
	boolean end;
	int time;
	Ver start;
	Ver finish;
	Ver[][] map;
	ArrayList<Integer> a;
	
	public static void main(String[] args) {
		UAFindMyClass fmc = new UAFindMyClass();
		maze = new File("maze.txt");
		fmc.rows = 0;
		fmc.cols = 0;
		fmc.end = false;
		fmc.linecount = 0;
		fmc.charCount = 0;
		fmc.openSpace = 0;
		fmc.closedSpace = 0;
		fmc.time = 0;
		fmc.readFile(maze);
		fmc.map = new Ver[fmc.graph.length][];
		fmc.ini(fmc.map);
		fmc.createMap(fmc.map, fmc.graph);
		//fmc.BFSMethod(fmc.graph, fmc.start);
		fmc.DFSMethod(fmc.graph);
	}
	
	public void ini(Ver[][]map) {
		for(int i = 0; i < map.length; i++) {
			map[i] = new Ver[a.get(i)];
		}
	}
	
	public void createMap(Ver[][] map, char[][] graph) {
		for(int i = 0; i < map.length; i++) {
			for(int j = 0; j < map[i].length; j++) {
				map[i][j] = new Ver(i,j, true);
			}
		}
	}
	
	public void printOut(char[][] graph) {
		for(int i = 0; i < graph.length; i++) {
			for(int j = 0; j < graph[i].length; j++) {
				System.out.print(graph[i][j]);
			}
			System.out.println("");
		}
	}
	
	public void DFSMethod(char[][] graph) {
		
		for(int i = 0; i < map.length; i++) {
				for(int j = 0; j < map[i].length; j++) {
					
					map[i][j].color = true;
					map[i][j].dist = Integer.MAX_VALUE;
					map[i][j].pi = null;
					
					if(graph[i][j] == '*') {
						map[i][j].color = false;
					}
				}
			}
		time = 0;
		for(int i = 0; i < map.length; i++) {
				for(int j = 0; j < map[i].length; j++) {
					if(map[i][j].color == true) {
						DFSVisit(graph, map[i][j]);
					}
				}
			}
	}
	
	public void DFSVisit(char[][] graph, Ver u) {
		time = time+1;
		u.dist = time;
		u.color = false;
		//System.out.println(u.x + " " + u.y);
		ArrayList<Ver> temp = adj(u, map);
		for(int i = 0; i < temp.size(); i++) {
			if(temp.get(i).color == true) {
				temp.get(i).pi = u;
				DFSVisit(graph, temp.get(i));
				
			}mover(temp.get(i), graph);
		}
		u.color = false;
		time = time+1;
		// no need for u.f = time.  Time is global.
	}
	
	public ArrayList<Ver> adj(Ver location, Ver[][] map) {
		ArrayList<Ver> aa = new ArrayList<Ver>();
		for(int i = 0; i < 4; i++) {
			if(i == 0 && location.x-1 > 0) {
				aa.add(map[location.x-1][location.y]);
			}
			if(i == 1 && location.y-1 > 0) {
				aa.add(map[location.x][location.y-1]);
			}
			if(i == 2 && location.x+1 < graph.length) {
				aa.add(map[location.x+1][location.y]);
			}
			if(i == 3 && location.y+1 < graph.length) {
				aa.add(map[location.x][location.y+1]);
			}
		}
		
		for(int i = 0; i < aa.size(); i++) {
			if(graph[aa.get(i).x][aa.get(i).y] != ' ' || aa.get(i).x <= 0 || aa.get(i).y <= 0 || aa.get(i).x > graph.length || aa.get(i).y > graph.length) {
				aa.remove(i);
			}
		}
		return aa;
	}
	
	
	public void BFSMethod(char[][] graph, Ver start) {
		Ver u = new Ver();
		for(int i = 0; i < map.length; i++) {
			for(int j = 0; j < map[i].length; j++) {
				
					u = map[i][j];
					//below: this is same as color is WHITE
					map[i][j].color = true;
					map[i][j].dist = Integer.MAX_VALUE;
					map[i][j].pi = null;
					u.color = true;
					u.dist = Integer.MAX_VALUE;
					u.pi = null;
					if(graph[i][j] == '*') {
						map[i][j].color = false;
					}
			}
		}
					//below: this is same as color is GRAY
					start.color = false;
					start.dist = 0;
					start.pi = null;
				
			
		
		Queue<Ver> q = new LinkedList<Ver>();
		q.add(start);
		while(!q.isEmpty()) {
			u = q.poll();
			
			ArrayList<Ver> temp = adj(u, map);
			
			for(int i = 0; i < temp.size(); i++) {
				if(temp.get(i).color == true && end == false) {
						temp.get(i).color = false;
						map[temp.get(i).x][temp.get(i).y].color = false;
						temp.get(i).dist = u.dist+1;
						temp.get(i).pi = u;
						q.add(temp.get(i));
						end = mover(temp.get(i), graph);
					}
			}
			u.color = false;
		}
	}
	
	public boolean mover(Ver temp, char[][] graph) {
		if(graph[temp.x][temp.y] == 'M') {
			System.out.println("Done!");
			System.out.println("Distance from start to finish complete correct steps: " + temp.dist);
			printOut(graph);
			return true;
		}
		if(graph[temp.x][temp.y] == ' ') {
			graph[temp.x][temp.y] = 'x';
			printOut(graph);
		}
		return false;
	}
	
	public void readFile(File maze) {
		//first try catch is just getting sizes
		a = new ArrayList<Integer>();
		try {
			BufferedReader br = new BufferedReader(new FileReader(maze.getName()));
			String line;
			linecount = 0;
			while((line = br.readLine()) != null){
				row = line.toCharArray();
				a.add(row.length);
				linecount++;
			}
			br.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		//we have sizes, so we init matrix.
		graph = new char[linecount][];
		for(int i = 0; i < linecount; i++) {
			graph[i] = new char[a.get(i)];
		}
		//filling the matrix with values here:
		findLocations(maze);
	}
	
	public void findLocations(File maze) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(maze.getName()));
			String line;
			int sec = 0;
			while((line = br.readLine()) != null){
					graph[sec] = line.toCharArray();
					if(foundStart == false || foundFinish == false) {
						for(int i = 1; i < graph[sec].length-1; i++) {
							if(foundStart == false) {
								if(graph[sec][i]=='S') {
									foundStart = true;
									start = new Ver(sec, i, true);
								}
							}
							if(foundFinish == false) {
								if(graph[sec][i]=='M') {
									foundFinish = true;
									finish = new Ver(sec, i, true);
								}
							}
						}
					}
				sec++;
			}
			foundStart = false;
			foundFinish = false;
			br.close();
			}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void aTest(char[][] graph) {
		//This is basically a size and shape test
		System.out.println("in aTest.....");
		System.out.println("charcount: " + charCount);
		System.out.println("graph.length: " + graph.length);
		System.out.println("graph[0].length: " + graph[0].length);
		for(int i = 0; i < graph.length; i++) {
			//System.out.println(graph[i].length);
			for(int j = 0; j < graph[i].length; j++) {
				System.out.print(graph[i][j]);
				//System.out.print(j + ", ");
			}
			System.out.println("");
		}
	}
	
	public void secondTest(char[][] graph) {
		//This is a secondary size and shape test with confirmation of start and finish locations
		System.out.println("in secondTest.....");
			for(int i = 0; i < graph.length; i++) {
				for(int j = 0; j < graph[i].length; j++) {
				if(foundStart == false) {
					if(graph[i][j]=='S') {
				//		System.out.println(i + " " + j + " is start location");
						foundStart = true;
						start = new Ver(i, j, true);
					}
				}
				if(foundFinish == false) {
					if(graph[i][j]=='M') {
				//		System.out.println(i + " " + j + " is finish location");
						foundFinish = true;
						finish = new Ver(i, j, true);
					}
				}
				System.out.print(graph[i][j]);
				}
				System.out.println("");
		}
	}
	
}

