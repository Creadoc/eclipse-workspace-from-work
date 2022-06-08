package trainingPack;

public class Trash {

	public static void main(String[] args) {
		int[] nums1 = {2,2,4,4};
		int[] nums2 = {2,2,4,4};
		findMedianSortedArrays(nums1, nums2);
	}
	public static double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int[] conc = new int[nums1.length + nums2.length];
        
        int count = 0;
        if(nums1.length == 0 && nums2.length < 2) {
        	System.out.println("first test");
        	return nums2[0];
        }
        if(nums2.length == 0 && nums1.length < 2) {
        	System.out.println("second test");
        	return nums1[0];
        }
        if(nums1.equals(null)) {
        	System.out.println("is this it?");
        	return nums2[0];
        }
        if(nums2.equals(null)) {
        	System.out.println("here??");
        	return nums1[0];
        }
        
        int tot = 0;
        for(int i = 0; i < nums1.length; i++){
            conc[i] = nums1[i];
            tot += conc[i];
        }
        
        for(int i = nums1.length; i < conc.length; i++){
            conc[i] = nums2[i - nums1.length];
            tot += conc[i];
        }
        
        if(tot == 0 && nums1.length < 1 && nums2.length < 1) {
        	System.out.println("there was nothing in it");
        	return 0;
        }
        
        if(tot == 0 && conc.length > 3) {
        	System.out.println("totally empty");
        	return 0;
        }
        
        System.out.println("conc length: " + conc.length);
        if(conc.length %2 == 0){
            double placer = (conc.length/2)+.5;
            System.out.println("placer?? " + placer);
            int first = conc[(int)placer-1];
            int second = conc[(int)placer];
            double answer = ((first+second)/2)+.5;
            
            System.out.println("first: " + first + " second: " + second + " placer: " + placer + " answer: " + answer);
            return answer;
        }else{
            double placer = conc.length/2+.5;
            double hold = conc[(int)placer];
            double ans = Math.round(hold/placer);
            System.out.println("placer: " + placer + " hold: " + hold + " ans: " + ans);
            return ans;
        }
    }
}
