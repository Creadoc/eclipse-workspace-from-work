package lab1Pack;

public class ExtendsExample extends Thread{
	
	 static int[] a = {1,2,3,4,5,6};
	static int start;
	static int stop;
	
	@Override
	public void run() {
		//System.out.println("Name: " + Thread.currentThread().getName());
		//System.out.println("ID: " + Thread.currentThread().getId());	
//		try {
//			Thread.sleep(5000);
//		}catch(InterruptedException ex) {
//			System.out.println("some error: " + ex);
//		}
		
			leftSide(a, start, stop);
		}

		
		public int leftSide(int[] a, int start, int stop){
//			try {
//				Thread.sleep(5000);
//			}catch(InterruptedException ex) {
//				System.out.println("some error: " + ex);
//			}
			
			
			int sum = 0;
			for(int i = start; i < stop; i++) {
				
				sum+= a[i];
				
		}
			System.out.println(sum);
			return sum;
		}
		
	
	public static void main(String[] args) {
//		Thread t1 = new ExtendsExample();
//		t1.start();
//		for(int i =0; i < 10; i++) {
//			Thread t1 = new ExtendsExample();
//			
//			t1.start();
//		}
		
		Thread t1 = new ExtendsExample();
		start = 0;
		stop = a.length/2;
		
		t1.start();
		try {
			t1.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		Thread t2 = new ExtendsExample();
		start = a.length/2;
		stop = a.length;
		
		t2.start();
		
//		Thread t2 = new ExtendsExample();
//		t2.start();
//		int mid = a.length/2;
		
	}
	
}

