package lab1Pack;

public class ThreadDriver {
	
	public static void main(String[] args) {
		int[] x = {1,2,3,4,5,6,7,8};
		//Modify the constructor input.
		//One thread should add all the odd numbers
		//Another thread should add all the even numbers.
		
		System.out.println("left side + right side after summation method: " + sum2(x));
	}
	
	public static int sum2(int[] a) {
		//Summation leftSum = new Summation(a, 0, a.length, 2);
		//Summation rightSum = new Summation(a, 1, a.length, 2);
		Summation even = new Summation(a, 0, a.length, 1, true);
		Summation odd = new Summation(a, 0, a.length, 1, false);
		//Thread leftThread = new Thread(leftSum);
		//Thread rightThread = new Thread(rightSum);
		Thread evenThread = new Thread(even);
		Thread oddThread = new Thread(odd);
		
//		leftThread.start();
//		rightThread.start();
		evenThread.start();
		oddThread.start();
		try {
//			leftThread.join();
//			System.out.println("The sum of the left side is: " + leftSum.getSum() + " " + leftThread.getName());
//			
//			System.out.println("The sum of the left side is: " + rightSum.getSum() + " " + rightThread.getName());
			evenThread.join();
			
			oddThread.join();
		}catch(InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("The sum of the even side is: " + even.getSum() + " " + evenThread.getName());
		System.out.println("The sum of the odd side is: " + odd.getSum() + " " + oddThread.getName());
//		int left = leftSum.getSum();
//		int right = rightSum.getSum();
		
		int ev = even.getSum();
		int od = odd.getSum();
//		System.out.println("even: " + even.getSum());
//		System.out.println("odd: " + odd.getSum());
//		System.out.println("calculation: " + (even.getSum() + odd.getSum()));
//		System.out.println("reprint: " + (ev + od));
		//return left + right;
		return ev + od;
	}

}
