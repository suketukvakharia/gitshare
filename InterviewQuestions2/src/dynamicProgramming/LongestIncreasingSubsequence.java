package dynamicProgramming;

import java.util.Arrays;

import org.junit.Test;



/**
 * @author suketu
 *
 */
public class LongestIncreasingSubsequence {

	@Test
	public void testSuccess() {
		int[] test1 = {1, 7, 10, 7, 15, 27, 29};
		
		int[] result = getLongestIncreasingSubSequence(test1);
		System.out.println(Arrays.toString(result));
		
		int[] test2 = {1, 9, 4, 66, 89, 9, 2, 3, 100, 10, 11, 14, 15};
		result = getLongestIncreasingSubSequence(test2);
		System.out.println(Arrays.toString(result));
	}
	
	/**
	 * Simple O(N^2) implementation done here first.
	 * @return
	 */
	public int[] getLongestIncreasingSubSequence(int[] a) {
		if(a.length <= 1) return a;
		
		int[] longestSubSequence = new int[a.length];
		int[] previousPointer = new int[a.length];
		int longestSubSequenceLength = 1;
		longestSubSequence[0] = 0;
		previousPointer[0] = -1;
		
		for(int i = 1; i < a.length; i++) {
			
			System.out.println("i is:" + i);
			System.out.println(Arrays.toString(a));
			System.out.println(Arrays.toString(longestSubSequence));
			System.out.println(Arrays.toString(previousPointer));
			System.out.println("Longest is:" + longestSubSequenceLength);
			
			// stop at first occurunce in longest subsequence where current value is larger than in array.
			int start = 0;
			int end = longestSubSequenceLength -1;
			while(start <= end) {
				int mid = (start + end) /2;
				if(a[longestSubSequence[mid]] > a[i]) {
					end = mid - 1;
				}
				else {
					start = mid + 1;
				}
			}
			
			System.out.println("start is:" + start);
			
			if(start == longestSubSequenceLength) {
				longestSubSequenceLength++;
				longestSubSequence[start] = i;
				previousPointer[i] = longestSubSequence[start -1]; // index of last guy is your previous.
			}
			else {
				longestSubSequence[start] = i;
				if(start != 0)
				previousPointer[i] = longestSubSequence[start-1];
				else 
					previousPointer[i] = -1;
			}
			
		}
		
		System.out.println(Arrays.toString(a));
		System.out.println(Arrays.toString(longestSubSequence));
		System.out.println(Arrays.toString(previousPointer));
		System.out.println("Longest is:" + longestSubSequenceLength);
		
		// rebuild the subsequence.
		int[] toReturn = new int[longestSubSequenceLength];
		int i = longestSubSequenceLength-1;
		int j = 0;
		toReturn[j++] = a[longestSubSequence[i]];
		i = previousPointer[longestSubSequence[i]];
		while(i != -1) {
			toReturn[j++] = a[i];
			i = previousPointer[i];
		}
		
		return toReturn;
	}
}
