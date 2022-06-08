package naiveBayesAlgorithmPack;

import java.io.*;
import java.util.*;

public class UASimpleClassifier {

	HashMap<Integer, CcObj> hMap;
	HashMap<Integer, CcObj> hMap2;
	ArrayList<String> classNames;
	ArrayList<ArrayList<CcObj>> trying;
	ArrayList<Double> classProb;
	int totalCount;

	public static void main(String[] args) {
		UASimpleClassifier sc = new UASimpleClassifier();
		sc.trying = new ArrayList<ArrayList<CcObj>>();
		sc.hMap = new HashMap<Integer, CcObj>();
		sc.hMap2 = new HashMap<Integer, CcObj>();
		sc.classNames = new ArrayList<String>();
		sc.classProb = new ArrayList<Double>();
		sc.totalCount = 0;
		sc.train("train.txt");

//		System.out.println("here is class names: ");
//		for(int i = 0; i < sc.classNames.size(); i++) {
//			System.out.println(sc.classNames.get(i));
//		}

	}

	public static double mean(double count, int total) {
		return count / total;
	}

	public static double variancepart(double mu, int count) {
		double a = count - mu;
		return a;
	}

	public static double sigmapart(double mu) {
		return 0;
	}

	// TAKEN FROM https://introcs.cs.princeton.edu/java/22library/Gaussian.java.html
	// ************************************************************************************************
	// return cdf(z, mu, sigma) = Gaussian cdf with mean mu and stddev sigma
	public static double cdf(double z, double mu, double sigma) {
		return cdf((z - mu) / sigma);
	}

	// return cdf(z) = standard Gaussian cdf using Taylor approximation
	public static double cdf(double z) {
		if (z < -8.0)
			return 0.0;
		if (z > 8.0)
			return 1.0;
		double sum = 0.0, term = z;
		for (int i = 3; sum + term != sum; i += 2) {
			sum = sum + term;
			term = term * z * z / i;
		}
		return 0.5 + sum * pdf(z);
	}

	// return pdf(x) = standard Gaussian pdf
	public static double pdf(double x) {
		return Math.exp(-x * x / 2) / Math.sqrt(2 * Math.PI);
	}

	// return pdf(x, mu, signma) = Gaussian pdf with mean mu and stddev sigma
	public static double pdf(double x, double mu, double sigma) {
		return pdf((x - mu) / sigma) / sigma;
	}

//************************************************************************************************    

	public void train(String filename) {
		fileReader(filename);
//		printHashMap(hMap);
		tester();
//		getMu();
//		getSigma();
		
		
		System.out.println("DONE TRAINING");
	}

	public void test(String filename) {
		fileReader(filename);
	}

	// predictor method
	public CcObj classify(String f1, String f2, String f3, int f4, int f5) {

		return null;
	}

	

	public void fileReader(String filename) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(filename));
			String line;
			String[] words;
			int count = 0;
			while ((line = br.readLine()) != null) {
				words = line.split(",");
				CcObj temp = new CcObj(words[0], words[1], words[2], words[3], words[4], words[5]);
				classNames.add(words[5]);
				System.out.println("temp hashcode value: ");
				System.out.println(temp.hashCode());
				if (!hMap.containsKey(count)) {
					hMap.put(count, temp);
				}
				if (!hMap2.containsKey(count)) {
					hMap2.put(temp.hashCode(), temp);
				}
				count++;
				totalCount++;
			}
			
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("There is a problem reading the file " + filename);
		}
		section();
	}

	
	
	public void section() {
		ArrayList<String> tru = new ArrayList<String>();
		for (String el : classNames) {
			if (!tru.contains(el)) {
				tru.add(el);
			}
		}
		classNames = (ArrayList<String>) tru.clone();
	}
	
	
	
	public void printHashMap(HashMap<Integer, CcObj> daClass) {
		System.out.println("here is the hashmap printing");
		for (Integer i : daClass.keySet()) {
			System.out.println(daClass.get(i));
		}
	}
	
	
	
	public void tester() {
		// I left this broken so you could look at this tomorrow and try again to fix it.
		trying = new ArrayList<ArrayList<CcObj>>();
		for (int i = 0; i < classNames.size(); i++) {
			trying.add(new ArrayList<CcObj>());
		}
		ArrayList<Integer> numerators = new ArrayList<Integer>();
		int cou = 0;
		Collection<CcObj> val = hMap2.values();
		System.out.println(val.toString());
		for(CcObj a : val) {
			int kk =  Character.getNumericValue(a.cl.charAt(5));
			for (int j = 0; j < classNames.size(); j++) {
				int tz = Character.getNumericValue(classNames.get(j).charAt(5));
				if(hMap.containsKey(j) && kk == tz) {
					trying.get(j).add(a);
					
				}
			}
			
		}
		getNumbers(numerators, cou);
		calculateClassProb(numerators);
	}
	
	public void getNumbers(ArrayList<Integer> numerators, int cou) {
		int kk = Character.getNumericValue(trying.get(0).get(0).cl.charAt(5));
		for(int i = 0; i < trying.size(); i++) {
			kk = Character.getNumericValue(trying.get(i).get(i).cl.charAt(5));
			for(int j = 0; j < trying.get(i).size(); j++) {
				if(Character.getNumericValue(trying.get(i).get(j).cl.charAt(5)) == kk) {
					cou++;
				}
			}
			numerators.add(cou);
			cou = 0;	
		}
	}
	
	public void calculateClassProb(ArrayList<Integer> numerators) {
		System.out.println("total: " + totalCount);
		for(int i = 0; i < numerators.size(); i++) {
			Double temp = Double.valueOf(numerators.get(i));
			classProb.add(temp/totalCount);
		}
	}
}
