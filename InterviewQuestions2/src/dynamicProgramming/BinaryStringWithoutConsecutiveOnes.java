package dynamicProgramming;

import org.junit.Test;



/**
 * http://www.geeksforgeeks.org/count-number-binary-strings-without-consecutive-1s/
 * 
 * @author suketu
 *
 */
public class BinaryStringWithoutConsecutiveOnes {
	
	@Test
	public void testSuccess() {
		int n = 3, numberOfStrings;
		
		numberOfStrings = getNumberOfBinaryStrings(n);
		System.out.println("Number of strings:" + numberOfStrings);
		
		n = 4;
		numberOfStrings = getNumberOfBinaryStrings(n);
		System.out.println("Number of strings:" + numberOfStrings);

	}

	private int getNumberOfBinaryStrings(int n) {
		if(n < 0) {
			return -1;
		}
		
		int[] zeroes = new int[n], ones = new int[n];
		
		zeroes[0] = 1; ones[0] = 1;
		
		zeroes[1] = zeroes[0] + ones[0];
		ones[1] = zeroes[0];
		
		for(int i = 1; i < n; i++) {
			zeroes[i] = zeroes[i-1] + ones[i-1];
			ones[i] = zeroes[i-1];
		}
		
		return zeroes[n-1] + ones[n-1];
	}

}
