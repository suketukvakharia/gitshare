package array;

import java.util.Arrays;
import java.util.Random;

public class MedianOfTwoSortedArrays {

    public static void main(String args[]) {
        
        
        int[] a = new int[50], b = new int[50];
        
        int[] c = new int[a.length + b.length];
        
        Random rand = new Random();
        for(int i = 0; i < c.length; i++) 
            c[i] = rand.nextInt();
        
        Arrays.sort(c); 
        
        for(int i = 0, j = 0; i < a.length || j < b.length; ) {
            int randInt = rand.nextInt(2);
            
            if( (randInt == 0 && i < a.length) || j >= b.length) {
                a[i] = c[i+j];
                i++;
            }
            else {
                b[j] = c[i+j];
                j++;
            }
        }
        
        int realMedian = findMedian(c);
        
        int calculatedMedian = findMedian(a,b);
    }
    
    

    private static int findMedian(int[] a, int[] b) {
        
        if(a == null || a.length == 0) return findMedian(b);
        
        if(b == null || b.length == 0) return findMedian(a);
        
        findMedian(a, 0, a.length-1, b, 0, b.length-1);
        
        
        return 0;
    }



    private static void findMedian(int[] a, int i, int j, int[] b, int k, int l) {
        
        
        int aMid = (i+j)/2;
        int aMidval = a[aMid];
        
        int bMid = binarySearch(aMidval, b, k, l);
        
        
        
    }






    private static int binarySearch(int searchVal, int[] b, int start, int end) {
        // TODO Auto-generated method stub
        return 0;
    }



    private static int findMedian(int[] c) {
        if(c == null || c.length == 0) return -1;
        
        if(c.length == 1) return c[0];
        
        if(c.length % 2 == 1) {
            return c[c.length/2];
        }
        else {
            return  (c[c.length/2] +c[c.length/2 -1])/2; 
        }
    }
}
