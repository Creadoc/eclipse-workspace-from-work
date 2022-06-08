package naiveBayesAlgorithmPack;

public class CcObj {
	
	String f1;
	String f2;
	String f3;
	String f4;
	String f5;
	String cl;
	
	public CcObj() {
		
	}
	
	public CcObj(String f1, String f2, String f3, String f4, String f5, String cl) {
		this.f1 = f1;
		this.f2 = f2;
		this.f3 = f3;
		this.f4 = f4;
		this.f5 = f5;
		this.cl = cl;
	}
	
	public String toString() {
		return cl + " " + f1 + " " + f2 + " " + f3 + " " + f4 + " " + f5 + " ";
	}

}
