package matrix;

import java.util.Arrays;

import org.junit.Test;

public class RotateMatrix {

    @Test
    public void testSucces() {
        int[][] a = {{0,1,2},
                    {3,4,5},
                    {6,7,8}};
        
        rotateMatrix(a);
        
        for(int[] row: a) {
            System.out.println(Arrays.toString(row));
        }
        
        
    }
    public static void rotateMatrix(int[][] a) {
        for(int i = 0; i < a.length; i++) {
            
            for(int j = a[i].length-1; j > i; j--) {
                int temp = a[i][j];
                a[i][j] = a[j][i];
                a[j][i] = temp;
            }
        }
        
        for(int i = 0; i < a.length; i++) {
            for(int j = 0; j < a[0].length/2; j++) {
                
                int temp = a[i][j]; 
                a[i][j] = a[i][a.length -1 -j];
                a[i][a.length -1 -j] = temp;
            }
        }
    }
}
