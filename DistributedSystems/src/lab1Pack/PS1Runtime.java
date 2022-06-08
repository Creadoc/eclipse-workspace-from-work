package lab1Pack;

import java.util.*;

public class PS1Runtime {

	static String[] location;
	static int rowLoc;
	static int colLoc;
	static int midRow;
	static int midCol;
	static boolean visited[][];
	static int[][]a;
	static int[][]c;
	static int[][]d;
	
	// need a way to make this dynamic as well.
	static int[][]b = {{1,2,1},{3,4,5},{2,1,2}};
	
	public static void main(String[]args) {
		
		//just setting values for a matrix.
		 a = new int[4][4];
		
		int[] aa = {1,2,3,4};
		a[0] = aa;
		int[] bb = {2,3,4,5};
		a[1] = bb;
		int[] cc = {6,5,4,3};
		a[2] = cc;
		int[] dd = {3,2,1,0};
		a[3] = dd;
		location = new String[2];
		getLocation();
		
		// size settings
		if(rowLoc > a.length || colLoc > a[0].length || rowLoc < 0 || colLoc < 0) {
			IndexOutOfBoundsException ie = new IndexOutOfBoundsException();
			ie.printStackTrace();
			System.out.println("out of bounds");
			return;
		}
		//printIntMat(a);
		UAThread thread = new UAThread();
		//c = thread.calculate(a, b);
		thread.run();
		
		
		
		
		System.out.println("Final Product: ");
		printIntMat(c);

	
	}
	
	// THIS WILL NEED TO BE REPLACED FOR ARGUMENTS IN THE MAIN METHOD!!!
	public static void getLocation() {
		Scanner in = new Scanner(System.in);
		System.out.println("enter row: ");
		rowLoc = Integer.parseInt(in.nextLine());
		location[0] = Integer.toString(rowLoc);
		System.out.println("enter column: ");
		colLoc = Integer.parseInt(in.nextLine());
		location[1] = Integer.toString(colLoc);
		//System.out.println("");
		System.out.println("location: " + rowLoc + ", " + colLoc);
		System.out.println("");
		in.close();
	}
	
	
	public static int[][] calculate(int[][] a, int[][] b){
		
		
		// a may not be square, but b will always be square.
		
		c = new int[a.length][a[0].length];
		// a temp of b here.
		d = new int[b.length][b[0].length];
		
		double tt = d.length/2.0;
		double zz = d[0].length/2.0;
		
		midRow = (int)Math.floor(tt);
		midCol = (int)Math.floor(zz);
		System.out.println("");
	
		BFSCMat(c);
		
		return c;
	}
	
	public static void BFSearch(int [][] aChunk, int[][] a) {
		int sum = 0;
		
		//making copy of b's data so we can multiply with A's data at the location:
		int[][] test = new int[b.length][b[0].length];
		for(int i = 0; i < b.length; i++) {
			for(int j = 0; j < b[0].length; j++) {
				test[i][j] = b[i][j];
			}
		}
		//System.out.println("starting BFS traversal and replacement");
		//creating a boolean matrix using argument matrix (referenceMat)
		int wid = aChunk.length;
		
		//null check
		if(wid == 0) {
			System.out.println("matrix is empty??");
			return;
		}
		int len = aChunk[0].length;
		boolean[][] visit = new boolean[wid][len];
		// boolean matrix created
		
		//beginning BFS traversal and swapping
		Queue<String> q = new LinkedList<>();
		
		q.add(midRow + "," + midCol);
		while(!q.isEmpty()) {
			String temp = q.remove();
			int ro = Integer.parseInt(temp.split(",")[0]);
			int co = Integer.parseInt(temp.split(",")[1]);
			if(ro < 0 || co < 0 || ro >= wid || co >= len || visit[ro][co]) continue;
			
			visit[ro][co] = true;
			//once location has been traversed, fill in integer matrix with
			// integers from original matrix in response to offset.  This also has out of bounds clearance as well.
			try {
				aChunk[ro][co] = a[ro + (rowLoc - midRow)][co + (colLoc - midCol)];
				test[ro][co] *= a[ro + (rowLoc - midRow)][co + (colLoc - midCol)];
			}catch(IndexOutOfBoundsException ie) {
				//if out of bounds, returns 0
				aChunk[ro][co] = 0;
				test[ro][co] = 0;
			}
			sum += test[ro][co];
			q.add(ro + "," + (co + 1)); //right
			q.add(ro + "," + (co - 1)); //left
			q.add((ro + 1) + "," + co); //down
			q.add((ro - 1) + "," + co); //up
			
		}
		c[rowLoc][colLoc] = sum;
		//System.out.println("and here is c so far: ");
		//printIntMat(c);
		//System.out.println("end of BFS traversal and replacement");
		
	}
	
	public static void BFSCMat(int [][] c) {
		
		
		//creating a boolean matrix using argument matrix
		int wid = c.length;
		
		//null check
		if(wid == 0) {
			System.out.println("matrix is empty??");
			return;
		}
		int len = c[0].length;
		boolean[][] visit = new boolean[wid][len];
		// boolean matrix created
		
		//beginning BFS traversal
		Queue<String> q = new LinkedList<>();
		
		q.add(rowLoc + "," + colLoc);
		while(!q.isEmpty()) {
			String temp = q.remove();
			int ro = Integer.parseInt(temp.split(",")[0]);
			int co = Integer.parseInt(temp.split(",")[1]);
			if(ro < 0 || co < 0 || ro >= wid || co >= len || visit[ro][co]) continue;
			
			visit[ro][co] = true;
			
			try {
				rowLoc = ro;
				colLoc = co;
			}catch(IndexOutOfBoundsException ie) {
				//if out of bounds, returns 0
				rowLoc = 0;
				colLoc = 0;
			}
			q.add(ro + "," + (co + 1)); //right
			q.add(ro + "," + (co - 1)); //left
			q.add((ro + 1) + "," + co); //down
			q.add((ro - 1) + "," + co); //up
			BFSearch(d, a);
		}
		
	}
	
	
	
	// PRINTING METHODS BELOW
	public static void printIntMat(int[][]visit) {
		for(int i = 0; i < visit.length; i++) {
			for(int j = 0; j < visit[0].length; j++) {
				System.out.print(visit[i][j] + ", ");
			}
			System.out.println("");
		}
		System.out.println("");
		System.out.println("");
	}
	
	public static void printBoolMat(boolean[][]visit) {
		for(int i = 0; i < visit.length; i++) {
			for(int j = 0; j < visit[0].length; j++) {
				System.out.print(visit[i][j] + ", ");
			}
			System.out.println("");
		}
		System.out.println("");
		System.out.println("");
	}
	
	public static void printMatrices(int[][] a, int[][] b, int[][] c) {
		System.out.println("a size: " + a.length + "x" + a[0].length);
		for(int i = 0; i < a.length; i++) {
			for(int j = 0; j < a[i].length; j++) {
				System.out.print(a[i][j] + ", ");
			}
			System.out.println("");
		}
		System.out.println("");
		System.out.println("c size: " + c.length + "x" + c[0].length);
		for(int i = 0; i < c.length; i++) {
			for(int j = 0; j < c[i].length; j++) {
				System.out.print(c[i][j] + ", ");
			}
			System.out.println("");
		}
		System.out.println("");
		System.out.println("b size: " + b.length + "x" + b[0].length);
		for(int i = 0; i < b.length; i++) {
			for(int j = 0; j < b[i].length; j++) {
				System.out.print(b[i][j] + ", ");
			}
			System.out.println("");
		}	
	}
	
	//PRINTING METHODS ABOVE!!
}


// THREAD CLASS
class UAThread extends Thread{
	
	static int midRow;
	static int midCol;
	static int[][] test;
	
	@Override
	public void run() {
		PS1Runtime.c = calculate(PS1Runtime.a, PS1Runtime.b);
	}
	
	
	public static int[][] calculate(int[][] a, int[][] b){
		
		
		// a may not be square, but b will always be square.
		// an empty c is initialized here.
		int[][] c = new int[a.length][a[0].length];
		// a temp of b here.
		int [][] d = new int[b.length][b[0].length];
		
		double tt = d.length/2.0;
		double zz = d[0].length/2.0;
		midRow = (int)Math.floor(tt);
		midCol = (int)Math.floor(zz);
		System.out.println("");
		BFSCMat(c, d);
		return c;
	}
	
	public static void BFSearch(int [][] aChunk, int[][] a, int[][] c) {
		int sum = 0;
		
		//making copy of b's data so we can multiply with A's data at the location:
		int[][] test = new int[PS1Runtime.b.length][PS1Runtime.b[0].length];
		for(int i = 0; i < PS1Runtime.b.length; i++) {
			for(int j = 0; j < PS1Runtime.b[0].length; j++) {
				test[i][j] = PS1Runtime.b[i][j];
			}
		}
		//System.out.println("starting BFS traversal and replacement");
		//creating a boolean matrix using argument matrix
		int wid = aChunk.length;
		
		//null check
		if(wid == 0) {
			System.out.println("matrix is empty??");
			return;
		}
		int len = aChunk[0].length;
		boolean[][] visit = new boolean[wid][len];
		// boolean matrix created
		
		//beginning BFS traversal and swapping
		Queue<String> q = new LinkedList<>();
		
		q.add(midRow + "," + midCol);
		while(!q.isEmpty()) {
			String temp = q.remove();
			int ro = Integer.parseInt(temp.split(",")[0]);
			int co = Integer.parseInt(temp.split(",")[1]);
			if(ro < 0 || co < 0 || ro >= wid || co >= len || visit[ro][co]) continue;
			
			visit[ro][co] = true;
			//once location has been traversed, fill in integer matrix with
			// integers from original matrix in response to offset.  This also has out of bounds clearance as well.
			try {
				aChunk[ro][co] = a[ro + (PS1Runtime.rowLoc - midRow)][co + (PS1Runtime.colLoc - midCol)];
				test[ro][co] *= a[ro + (PS1Runtime.rowLoc - midRow)][co + (PS1Runtime.colLoc - midCol)];
			}catch(IndexOutOfBoundsException ie) {
				//if out of bounds, returns 0
				aChunk[ro][co] = 0;
				test[ro][co] = 0;
			}
			sum += test[ro][co];
			q.add(ro + "," + (co + 1)); //right
			q.add(ro + "," + (co - 1)); //left
			q.add((ro + 1) + "," + co); //down
			q.add((ro - 1) + "," + co); //up
			
		}
		c[PS1Runtime.rowLoc][PS1Runtime.colLoc] = sum;
		
		//just printing for now.
		System.out.println("and here is c so far: ");
		printIntMat(c);
		
	}
	
	public static void BFSCMat(int [][] c, int[][]d) {
		
		
		//creating a boolean matrix using argument matrix
		int wid = c.length;
		
		//null check
		if(wid == 0) {
			System.out.println("matrix is empty??");
			return;
		}
		int len = c[0].length;
		boolean[][] visit = new boolean[wid][len];
		// boolean matrix created
		
		//beginning BFS traversal
		Queue<String> q = new LinkedList<>();
		
		q.add(PS1Runtime.rowLoc + "," + PS1Runtime.colLoc);
		while(!q.isEmpty()) {
			String temp = q.remove();
			int ro = Integer.parseInt(temp.split(",")[0]);
			int co = Integer.parseInt(temp.split(",")[1]);
			if(ro < 0 || co < 0 || ro >= wid || co >= len || visit[ro][co]) continue;
			
			visit[ro][co] = true;
			
			try {
				PS1Runtime.rowLoc = ro;
				PS1Runtime.colLoc = co;
			}catch(IndexOutOfBoundsException ie) {
				//if out of bounds, returns 0
				PS1Runtime.rowLoc = 0;
				PS1Runtime.colLoc = 0;
			}
			q.add(ro + "," + (co + 1)); //right
			q.add(ro + "," + (co - 1)); //left
			q.add((ro + 1) + "," + co); //down
			q.add((ro - 1) + "," + co); //up
			BFSearch(d, PS1Runtime.a, c);
		}
		
	}
	
	public static void printIntMat(int[][]visit) {
		for(int i = 0; i < visit.length; i++) {
			for(int j = 0; j < visit[0].length; j++) {
				System.out.print(visit[i][j] + ", ");
			}
			System.out.println("");
		}
		System.out.println("");
		System.out.println("");
	}
	
	
	
	public static int[][] calculate2(int[][] a, int[][] b){
		
		// need to figure out the size of c from a and b here.
				
				
		return null;
	}
}
