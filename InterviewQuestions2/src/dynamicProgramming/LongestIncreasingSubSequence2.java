package dynamicProgramming;

import org.junit.Test;

import java.util.Arrays;

/**
 * Original Question: https://careercup.com/question?id=5708239824486400
 */
public class LongestIncreasingSubSequence2 {
    @Test
    public void testSuccess() {
        int[] test1 = {1, 7, 10, 7, 15, 27, 29};

        int[] result = findLongestIncreasingSubSeq(test1);
        System.out.println(Arrays.toString(test1));
        System.out.println(Arrays.toString(result));

        int[] test2 = {1, 9, 4, 66, 89, 9, 2, 3, 100, 10, 11, 14, 15};
        result = findLongestIncreasingSubSeq(test2);
        System.out.println(Arrays.toString(test2));
        System.out.println(Arrays.toString(result));
    }

    private int[] findLongestIncreasingSubSeq(int[] arg) {

        // sequence keeper
        int[] longestSubsequence = new int[arg.length];
        int[] previousPointers = new int[arg.length];
        int longestSequenceLength;

        // bootstrap first index
        longestSubsequence[0] = 0;  // index to arg array only
        previousPointers[0] = -1;  // points to nothing
        longestSequenceLength = 1;

        for (int i = 1; i < arg.length; i++) {
            // iterate over longest subsequence and find where to add i
            int toInsertIndex = 0;
            while (toInsertIndex < longestSequenceLength && arg[i] > arg[longestSubsequence[toInsertIndex]]) {
                toInsertIndex++;
            }

            // set the sequnce in
            longestSubsequence[toInsertIndex] = i;
            previousPointers[i] = toInsertIndex == 0 ? -1 : longestSubsequence[toInsertIndex -1];


            if (toInsertIndex == longestSequenceLength) {
                longestSequenceLength++;
            }

//            System.out.println("i is:" + i);
//            System.out.println(Arrays.toString(arg));
//            System.out.println(Arrays.toString(longestSubsequence));
//            System.out.println(Arrays.toString(previousPointers));
//            System.out.println("Longest is:" + longestSequenceLength);
        }

        int[] toReturn = new int[longestSequenceLength];
        int i = longestSequenceLength-1;
        int j = longestSequenceLength-1;
        toReturn[j--] = arg[longestSubsequence[i]];
        i = previousPointers[longestSubsequence[i]];
        while(i != -1) {
            toReturn[j--] = arg[i];
            i = previousPointers[i];
        }
        return toReturn;
    }
}
