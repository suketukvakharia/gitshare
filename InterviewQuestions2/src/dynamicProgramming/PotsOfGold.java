package dynamicProgramming;



/**
 * http://www.careercup.com/question?id=5707979286380544
 * @author suketu
 *
 */
public class PotsOfGold {
    
    
    
    /**
     * The pots of gold game.
     * First player goes first. Assumes the second player plays perfectly as well.
     * @param goldArray
     * @return
     */
    public int maxGoldSecondPerfect(int[] goldArray) {
        
        
        int[][] cacheGrid = new int[goldArray.length][goldArray.length];
        
        for(int i = 0; i < goldArray.length; i++) 
            for(int j = 0; j < goldArray.length; j++)
                cacheGrid[i][j] = -1;
        
        return maxGoldSecondPerfect(goldArray, 0, goldArray.length-1, cacheGrid);
    }

    private int maxGoldSecondPerfect(int[] goldArray, int start, int end, int[][] cacheGrid) {
        
        // dynamic allocation based on start/end.
        if(cacheGrid[start][end] != -1) {
            return cacheGrid[start][end];
        }
        
        // pick start
        int myGold = goldArray[start] + Math.min(maxGoldSecondPerfect(goldArray, start-2, end, cacheGrid), 
                maxGoldSecondPerfect(goldArray, start-1, end-1, cacheGrid));
        
        int myGoldEnd = goldArray[end] + Math.min(maxGoldSecondPerfect(goldArray, start, end-2, cacheGrid), 
                maxGoldSecondPerfect(goldArray, start-1, end-1, cacheGrid));
        
        int toReturn =  Math.max(myGold, myGoldEnd);
        cacheGrid[start][end] = toReturn;
        return toReturn;
    }

}
