package matrix;

import org.junit.Test;


/**
 * www.careercup.com/question?id=5661939564806144
 * 
 * 
 * @author suketu
 *
 */
public class PrintMatrixDiagonally {

    
    @Test
    public void testSuccess() {
        
        int[][] a = {   {1, 2, 3}, 
                        {4, 5, 6}, 
                        {7, 8, 9}};
        
        printMatrixDiagonallyLeftTop(a);
    }
    /**
     * Approaches for this.
     * 1 enqueue 0,0 and process them out as you get them. Keep order left bottom etc.
     * Try and do two for loops to get them
     */
    public static void printMatrixDiagonallyLeftTop(int[][] a) {
        
        if(a == null) {
            return;
        }
        
        // print up to the diagonal first.
        

        System.out.println("Left Top first");
        for(int i = 0; i < 2 * a.length -2; i++) {
            int j = i >= a.length ? i - a.length + 1 : 0;
            for(; j <= i && j < a.length; j++) {
                System.out.print(a[j][i-j] + " ");
            }
            System.out.println();
        }
        
        System.out.println("From bottom Right");
        for(int i = 2 * a.length -2; i >= 0; i--) {
            int j = i >=a.length ? i - a.length + 1 :0;
            for(; j <= i && j < a.length; j++) {
                System.out.print(a[j][i-j] + " ");
            }
            System.out.println();
        }
        
        System.out.println("From bottom Right Reversed");
        for(int i = 2 * a.length -2; i >= 0; i--) {
            int j = i >=a.length ? a.length -1 :i;
            for(; j >= 0 && i -j < a.length; j--) {
                System.out.print(a[j][i-j] + " ");
            }
            System.out.println();
        }
        System.out.println();
        
        System.out.println("From Top right top to down");
        for(int i = -1 * (a.length -1); i < a.length; i++) {
            
            int j = i < 0 ? i * -1 : 0;
            for(; j < a.length && i+j < a.length; j++) {
                System.out.print(a[i+j][j] + " ");
            }
            System.out.println();
        }
        
        System.out.println("From Top right top to down");
        for(int i = -1 * (a.length -1); i < a.length; i++) {
            
            int j = i < 0 ? a.length - 1: a.length - i - 1;
            for(; j >= 0 && i+j >= 0; j--) {
                System.out.print(a[i+j][j] + " ");
            }
            System.out.println();
        }
        
        
        
    }
}
