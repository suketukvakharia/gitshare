package array;

import java.util.Arrays;
import java.util.Random;







import org.junit.Assert;
import org.junit.Test;

public class GetKthElementOfTwoSortedArray {
    
    @Test
    public void testSuccess() throws Exception {
        int[] a = new int[100], b = new int[500]; 
        int[] c = new int[a.length + b.length];
        
        int genMax = 100000;
        Random rand = new Random();
        for(int i = 0; i < a.length; i++) {
            a[i] = rand.nextInt(genMax);
            c[i] = a[i];
        }
        for(int j = 0; j < b.length; j++) {
            b[j] = rand.nextInt(genMax);
            c[a.length + j] = b[j];
        }
        
        Arrays.sort(a);
        Arrays.sort(b);
        Arrays.sort(c);
        
        System.out.println(Arrays.toString(a));
        System.out.println(Arrays.toString(b));
        System.out.println(Arrays.toString(c));
        
        for(int k = 0; k < c.length; k++) {
            
            int actual = getKthElement(a, b, k + 1);
            int expected = c[k];
            
            Assert.assertEquals(expected, actual);
        }
    }
    
    public static int getKthElement(int[] a, int [] b, int k) throws Exception {
        
        int ast = 0, aend = a.length -1, bst = 0, bend = b.length -1;
        
        while(true) {
            
            if(ast == aend && bst == bend) {
                if(k >= ast + bst + 2) {
                    return a[ast] > b[bst] ? a[ast] : b[bst];
                } else {
                    return a[ast] > b[bst] ? b[bst] : a[ast];
                }
            }
            int amid = (ast + aend) /2, bmid = (bst + bend)/2;
            int aval = a[amid], bval = b[bmid];
//            System.out.println("amid:" + amid + " ast:" + ast + " aend:" + aend + " aval:" + aval + " bmid:" + bmid + " bend:" + bend + " bst:" + bst + " bval:" + bval + " k:" + k);
            if(k >= amid + bmid + 2) {
                if(aval > bval) {
                    bst = bmid + 1;
                }
                else {
                    ast = amid + 1;
                }
            }
            else {
                if(aval < bval) {
                    bend = bmid -1;
                }
                else {
                    aend = amid - 1;
                }
            }
            
            if(ast > aend) {
                return b [k - ast -1];
            }
            else if(bst > bend) {
                return a[k - bst -1];
            }
        }
    }

}
