package dynamicProgramming;

import org.junit.Assert;
import org.junit.Test;



/**
 * http://www.careercup.com/question?id=5931067269709824
 * @author suketu
 *
 */
public class TwoCharSubSequence {
    
    
    @Test 
    public void testSuccess() {
        String a = "abab";
        Assert.assertTrue(this.lcsTwoCharRepeats(a));
    }
    
    @Test 
    public void testFailure() {
        String a = "abba";
        Assert.assertFalse(this.lcsTwoCharRepeats(a));
    }
    
    @Test 
    public void testSuccessLong() {
        String a = "acbdaghfb ";
        Assert.assertTrue(this.lcsTwoCharRepeats(a));
        a = "abcdacb";
        Assert.assertTrue(this.lcsTwoCharRepeats(a));
        a = "xxyy";
        Assert.assertTrue(this.lcsTwoCharRepeats(a));
    }
    
    /**
     * This is a variation of the DP problem of finding the smallest edit distnace.
     * Instead of having 1 for each edit, we have 1 for each match.
     * We only match characters after current, thus the j < i!
     * If we get two matches we have found our sequence.
     * Backtracking is not implemented here.
     * @param a
     * @return
     */
    public boolean lcsTwoCharRepeats(String a) {
        
        if(a == null || a.isEmpty() || a.length() == 1) return false;
        
        String b = a;
        
        int[][] grid = new int[a.length()+1][b.length()+1];
        
        for(int i = 0; i < a.length(); i++) {
            for(int j = 0; j < i; j++) {
                
                int diag = grid[i][j], top = grid[i][j+1], left = grid[i+1][j];
                
                if(a.charAt(i) == b.charAt(j)) {
                    diag++;
                }
                
                grid[i+1][j+1] = Math.max(Math.max(top,left), diag);
                
                if(grid[i+1][j+1] == 2) return true;
            }
        }
        
        return false;
    }

}
