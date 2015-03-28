package dynamicProgramming;

import org.junit.Assert;
import org.junit.Test;


/**
 * Solution for: http://www.geeksforgeeks.org/how-to-print-maximum-number-of-a-using-given-four-keys/
 * I got it wrong the first time.
 * @author suketu
 *
 */
public class PrintMaximumAs {

	
	@Test
	public void testSuccess() {
		
		testInputOutput(1, 1);
		
		testInputOutput(2, 2);
		
		testInputOutput(3, 3);

		testInputOutput(4, 4);

		testInputOutput(5, 5);
		
		testInputOutput(6, 6);
		
		testInputOutput(7, 9);
		
		testInputOutput(11, 27);
		
		int N;
		 
	    // for the rest of the array we will rely on the previous
	    // entries to compute new ones
	    for (N=1; N<=20; N++)
	        System.out.println(String.format("Maximum Number of A's with %d keystrokes is %d\n",
	               N, findoptimal(N)));
	}

	private void testInputOutput(int input, int expectedResult) {
		int result;
		result = pirntMaximumAs(input);
		Assert.assertEquals(expectedResult, result);
	}
	
	// A recursive function that returns the optimal length string 
	// for N keystrokes
	int findoptimal(int N)
	{
	    // The optimal string length is N when N is smaller than 7
	    if (N <= 6)
	        return N;
	 
	    // Initialize result
	    int max = 0;
	 
	    // TRY ALL POSSIBLE BREAK-POINTS
	    // For any keystroke N, we need to loop from N-3 keystrokes
	    // back to 1 keystroke to find a breakpoint 'b' after which we
	    // will have Ctrl-A, Ctrl-C and then only Ctrl-V all the way.
	    int b;
	    for (b=N-3; b>=1; b--)
	    {
	            // If the breakpoint is s at b'th keystroke then
	            // the optimal string would have length
	            // (n-b-1)*screen[b-1];
	            int curr = (N-b-1)*findoptimal(b);
	            if (curr > max)
	                max = curr;
	     }
	     return max;
	}
	
	public static int pirntMaximumAs(int n) {
		
		int start = -1;
		int[] cachedMaxByStep = new int[n];
		int[] copiedMaxByStep = new int[n];
		int[] selectedMaxByStep = new int[n];
		int currentlyPrintedAs = 0;
		int copiedCurrently = 0;
		int selectedCurrently = 0;
		
		printMaximumAs(start, n, cachedMaxByStep, copiedMaxByStep, selectedMaxByStep, currentlyPrintedAs, copiedCurrently, selectedCurrently);
		
		
		return cachedMaxByStep[n-1];
	}

	private static void printMaximumAs(int i, int n, int[] printedMaxByStep,
			int[] copiedMaxByStep, int[] selectedMaxByStep, int currentlyPrintedAs, int copiedCurrently,
			int selectedCurrently) {
		// if ended.
		if(i == n-1) {
			updateMaxIfNeeded(printedMaxByStep, copiedMaxByStep, selectedMaxByStep, 
					currentlyPrintedAs, copiedCurrently, selectedCurrently, i);
			return;
		}
		// if there is a path better than ours already.
		if(i != -1 && currentlyPrintedAs < printedMaxByStep[i] 
				&& copiedCurrently < copiedMaxByStep[i] && 
				( selectedMaxByStep[i] > selectedCurrently || copiedMaxByStep[i] > selectedCurrently)) {
			return;
		}
		
		// take the four possible actions.
		
		// paste.
		if(copiedCurrently > 0) {
			currentlyPrintedAs += copiedCurrently;
			int temp = selectedCurrently;
			selectedCurrently = 0;
			updateMaxIfNeeded(printedMaxByStep, copiedMaxByStep, selectedMaxByStep, 
					currentlyPrintedAs, copiedCurrently, selectedCurrently, i+1);
			printMaximumAs(i+1, n, printedMaxByStep, copiedMaxByStep, selectedMaxByStep, 
					currentlyPrintedAs, copiedCurrently, selectedCurrently);
			currentlyPrintedAs -= copiedCurrently;
			selectedCurrently = temp;
		}
		
		// copy
		if(selectedCurrently > 0) {
			int temp = copiedCurrently;
			copiedCurrently = selectedCurrently;
			updateMaxIfNeeded(printedMaxByStep, copiedMaxByStep, selectedMaxByStep, 
					currentlyPrintedAs, copiedCurrently, selectedCurrently, i+1);
			printMaximumAs(i+1, n, printedMaxByStep, copiedMaxByStep, selectedMaxByStep, 
					currentlyPrintedAs, copiedCurrently, selectedCurrently);
			copiedCurrently = temp;
		}
		
		// select
		if(currentlyPrintedAs > 0) {
			int temp = selectedCurrently;
			selectedCurrently = currentlyPrintedAs;
			updateMaxIfNeeded(printedMaxByStep, copiedMaxByStep, selectedMaxByStep, 
					currentlyPrintedAs, copiedCurrently, selectedCurrently, i+1);
			printMaximumAs(i+1, n, printedMaxByStep, copiedMaxByStep, selectedMaxByStep, 
					currentlyPrintedAs, copiedCurrently, selectedCurrently);
			selectedCurrently = temp;
		}
		
		// print A
		currentlyPrintedAs++;
		int temp = selectedCurrently;
		selectedCurrently = 0;
		updateMaxIfNeeded(printedMaxByStep, copiedMaxByStep, selectedMaxByStep, 
				currentlyPrintedAs, copiedCurrently, selectedCurrently, i+1);
		printMaximumAs(i+1, n, printedMaxByStep, copiedMaxByStep, selectedMaxByStep, 
				currentlyPrintedAs, copiedCurrently, selectedCurrently);
		selectedCurrently = temp;
		currentlyPrintedAs--;
		
	}

	private static void updateMaxIfNeeded(int[] printedMaxByStep,
			int[] copiedMaxByStep, int[] selectedMaxByStep,
			int currentlyPrintedAs, int copiedCurrently, int selectedCurrently,
			int i) {
		if(printedMaxByStep[i] < currentlyPrintedAs) {
			printedMaxByStep[i] = currentlyPrintedAs;
		}
		if(copiedMaxByStep[i] < copiedCurrently) {
			copiedMaxByStep[i] = copiedCurrently;
		}
		if(selectedMaxByStep[i] < selectedCurrently) {
			selectedMaxByStep[i] = selectedCurrently;
		}
	}
}
