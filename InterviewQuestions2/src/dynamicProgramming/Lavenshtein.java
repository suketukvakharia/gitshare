package dynamicProgramming;

import java.util.Arrays;

import org.junit.Test;


/**
 * http://en.wikipedia.org/wiki/Levenshtein_distance
 * @author suketu
 *
 */
public class Lavenshtein {

    @Test
    public void testSuccess() {
        String a = "kitten", b = "sitting";
        
        System.out.println(lavenshteinDistance(b, a));
    }
    
    /**
     * a = sitting
     * b = kitten
     * 
     * @param a
     * @param b
     * @return
     */
    public static int lavenshteinDistance(String a, String b) {
        
        // the first row and column are zero for reference.
        int[][] grid = new int[a.length()+1][b.length()+1];
        
        for(int i = 0; i < grid.length; i++) {
            grid[i][0] = i;
        }
        
        for(int i = 0; i < grid[0].length; i++) {
            grid[0][i] = i;
        }
        
        for(int i = 0; i < a.length(); i++) {
            for(int j = 0; j < b.length(); j++) {
                
                // left, right and diag choices
                int diag = grid[i][j], left = grid[i+1][j] + 1, top = grid[i][j+1] + 1;
                
                if(a.charAt(i) != b.charAt(j)) {
                    diag++;
                }
                
                grid[i+1][j+1] = Math.min(Math.min(diag, left),top);
            }
        }
        
        for(int[] gridrow: grid) {
            System.out.println(Arrays.toString(gridrow));
        }
        return grid[grid.length-1][grid[0].length-1];
    }
}
