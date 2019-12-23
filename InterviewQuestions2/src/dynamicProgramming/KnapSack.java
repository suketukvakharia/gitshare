package dynamicProgramming;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

/**
 * The problem is defined fully here: https://www.geeksforgeeks.org/0-1-knapsack-problem-dp-10/
 */
public class KnapSack {

    /**
     * Maximizes the value by fitting items into the sack.
     *
     * @param values
     * @param weights
     * @param sackSize
     * @return
     */
    private int maxLoot(int[] values, int[] weights, int sackSize) {
        // validations
        if (values.length != weights.length && values.length != 0 && weights.length != 0 && sackSize > 0) {
            throw new RuntimeException("values and weights length's must be the same and greater than zero");
        }

        // Memory # of items x size of sack
        int[][] dpGrid = new int[values.length + 1][sackSize + 1];

        // # of ops values x size of sack.
        for (int i = 1; i <= values.length; i++) {

            int currentVal = values[i - 1];
            int currentWeight = weights[i - 1];

            for (int j = 1; j <= sackSize; j++) {

                // get previous max
                int previousMaxVal = dpGrid[i - 1][j];

                // get max if we include this value
                int currentMaxVal = 0;
                if (j >= currentWeight) {
                    currentMaxVal = dpGrid[i - 1][j - currentWeight] + currentVal;
                }

                dpGrid[i][j] = Math.max(currentMaxVal, previousMaxVal);
            }
        }
        for (int[] dpGridElement : dpGrid) {
            System.err.println(Arrays.toString(dpGridElement));
        }
        System.err.println("\n\n________ DONE ________\n\n");
        return dpGrid[values.length][sackSize];
    }

    @Test
    public void helloWorld() {
        assertEquals(1, maxLoot(new int[]{1}, new int[]{1}, 1));
        assertEquals(1, maxLoot(new int[]{1, 2}, new int[]{1, 2}, 1));
        assertEquals(2, maxLoot(new int[]{1, 2}, new int[]{1, 1}, 1));

        assertEquals(3, maxLoot(new int[]{1, 2}, new int[]{1, 1}, 3));

        assertEquals(8, maxLoot(new int[]{8, 1, 2}, new int[]{3, 1, 1}, 3));
    }
}
