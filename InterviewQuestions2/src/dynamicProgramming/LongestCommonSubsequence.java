package dynamicProgramming;

import org.junit.Test;

public class LongestCommonSubsequence {
    
    @Test 
    public void testSuccess() {
        
        String a = "isabt", b = "this is not my life";
        
        String c = getLongestSubsequence(a, b);
        
        System.out.println(c.length());
        System.out.println(c);
    }
    
    
    public String getLongestSubsequence(String a, String b) {
        
        if(a == null || b == null || a.isEmpty() || b.isEmpty()) return "";
        
        int[][] grid = new int[b.length()+1][a.length()+1];
        
        for(int i = 0; i < a.length(); i++) {
            for(int j = 0; j < b.length(); j++) {
                int top = grid[j][i+1], left = grid[j+1][i], diag = grid[j][i];
                if(a.charAt(i) == b.charAt(j)) diag++;
                grid[j+1][i+1] = Math.max(Math.max(top, left),diag);
            }
        }
        
        // recreating the string is not so easy!!!
        
        // start at the end.
        String toReturn = "";
        for(int i = a.length(), j = b.length(); i >0 && j >0; ) {
            
            int current = grid[j][i], top = grid[j-1][i], left = grid[j][i-1];
            
            if(top == current) {
                j--;
            }
            else if(left == current) {
                i--;
            }
            else {
                toReturn = a.charAt(i-1) + toReturn;
                j--; i--;
            }
        }
        
        return toReturn;
    }

}
