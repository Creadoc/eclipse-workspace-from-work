package lab1Pack;
import java.io.*;
import java.util.*;

public class Lab1 {
	
	public static HashMap <String, ArrayList<String>> map;
	
	public static void main(String[] args){
		
		map = new HashMap<>();
		String filePath = args[0];
		File file = new File(filePath);
		isFileDirectory(file);
		
		String search = args[1];
		System.out.println("");
		if(map.containsKey(search)) {
			System.out.println("filename #ofpaths longestStringPath");
			System.out.println("-------- -------- -----------------");
			System.out.println(search + " \t " + map.get(search).size() + " \t " + map.get(search).get(map.get(search).size()-1));
		}		
		System.out.println();
		System.out.println();
			for(int i = 0; i < map.get(search).size(); i++) {
				System.out.println(map.get(search).get(i));
			}
			System.out.println();
			System.out.println();
			System.out.println("\""+search+"\"" + ", " + map.get(search));
	      
	}
	
	public static void isFileDirectory(File file){
			
			//create a list of files for iteration in directory.
			File[] files = file.listFiles();
			
			for(File f: files) {
				if(f.isDirectory()) {
					// if file is directory, recurse to next directory/sub-directory.
					isFileDirectory(f);					
				}else {
					
					ArrayList<String> list = map.get(f.getName());
					
					// if the arraylist is null, recreate list, add this list to map with key of the file's name.
					if(list == null) {
						list = new ArrayList<String>();
						map.put(f.getName(), list);
					}
					//add filepath to current list in question.
					list.add(f.getAbsolutePath());	
				}			
			}
	}
}
