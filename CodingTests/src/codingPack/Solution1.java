package codingPack;

public class Solution1 {
	
	public static void main(String[] args) {
	
		int[] arr = {1,0,2,3,0,4,5,0};
		
		duplicateZeros(arr);
		
	}
	
	public static void duplicateZeros(int[] arr) {
        for(int i = 0; i < arr.length; i++){
            
                if(arr[i] ==0){
                    System.out.println("starting at: " + i);
                    for (int j = (i-1); j >= 0; j--){
                    	System.out.println("j: " + j);
                    	if(arr[j] == 0) {
                    		if(j < arr.length-1) {
                    			arr[j + 1] = arr[j];
                    	
                    			arr[j+1] = 0;
                    			j--;
                         	}
                    		
                    		
                    		
                    		
                    	}
                    }
                }
        }
                    
                    System.out.println("SPACE");
                    for(int i = 0; i < arr.length; i++) {
                    	System.out.println(arr[i]);
                    }

                    //arr[i+1] = 0;
                //}
            
        //}
    }

}
