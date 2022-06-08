package lab1Pack;
import java.util.*;

public class Summation extends Thread{
	int[] x;
	int start;
	int stop;
	private int sum;
	int incrementer;
	String name;
	boolean even;

	public Summation(int[] x, int start, int stop, int incrementer) {
		this.x = x;
		this.start = start;
		this.stop = stop;
		this.incrementer = incrementer;
		
	}
	
	public Summation(int[] x, int start, int stop, int incrementer, boolean even) {
		this.x = x;
		this.start = start;
		this.stop = stop;
		this.incrementer = incrementer;
		this.even = even;
		
	}
	
	@Override
	public void run() {
		//System.out.println("starttime: " + System.currentTimeMillis()/1000);
		int sum = 0;
		for(int i = this.start; i < this.stop; i+=incrementer) {
			
			if(x[i]%2 == 0 && this.even == true) {
				sum += x[i];
			}
			else if(x[i]%2 == 1 && this.even == false) {
				sum += x[i];
			}
		}
		
		this.sum = sum;
	}

	public int getSum() {
		return sum;
	}

	public void setSum(int sum) {
		this.sum = sum;
	}
	
	
}

