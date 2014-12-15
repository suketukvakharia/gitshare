package search;

import java.util.Arrays;

import org.junit.Test;

/**
 * http://www.careercup.com/question?id=5147801809846272
 * @author suketu
 *
 */
public class LargestIncreasingSequenceMatrix {
    
    @Test
    public void testSuccess() {
        
        int[][] argArr = {  {1,5,9}, 
                            {2,3,8},
                            {4,6,7}
                         };
        
        int[] largestSequence = getLargestIncreasingSequence(argArr);
        System.out.println(Arrays.toString(largestSequence));
    }
    
    /**
     * Special case of the increasing sequence. We only care about adjacent increasing numbers. 
     * Additionally we are guaranteed that the matrix is a square.
     * Additionally we are guaranteed that the matrix has numbers 1 to n^2 without repeats that is they are distinct. .
     * @param matrix
     * @return
     */
    public int[] getLargestIncreasingSequence(int[][] matrix) {
        
        int n = matrix.length;
        
        int[][] pointPositions = new int[n*n][2];
        
        for(int i = 0; i < matrix.length; i++) {
            for(int j = 0; j < matrix[i].length; j++) {
                pointPositions[matrix[i][j]-1][0] = i; 
                pointPositions[matrix[i][j]-1][1] = j; 
            }
        }
        
        
        // now see how many points in the array are next to each other and find the largest sequence.
        int largestSequenceCount = 0;
        int largestSequenceEndsAt = 0;
        int currentMaxCount = 0;
        for(int i = 0; i < pointPositions.length-1; i++) {
            System.out.println(Arrays.toString(pointPositions[i]));
            if(areNextToEachOther(pointPositions, i, i+1)) {
                currentMaxCount++;
            }
            else {
                currentMaxCount = 0;
            }
            
            if(currentMaxCount > largestSequenceCount) {
                largestSequenceCount = currentMaxCount;
                largestSequenceEndsAt = i+1;
            }
        }
        
        int toReturn[] = new int[largestSequenceCount+1];
        
        for(int i = 0; i <= largestSequenceCount; i++) {
            toReturn[i] = largestSequenceEndsAt + i + 1 - largestSequenceCount;
        }
        return  toReturn;
    }

    private boolean areNextToEachOther(int[][] pointPositions, int i, int j) {
        
        return ( ( pointPositions[i][0] == pointPositions[j][0] && Math.abs(pointPositions[i][1] - pointPositions[j][1]) == 1)
                || ( pointPositions[i][1] == pointPositions[j][1] && Math.abs(pointPositions[i][0] - pointPositions[j][0]) == 1));
    }

}
