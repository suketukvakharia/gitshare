package dynamicProgramming;

import org.junit.Test;

import util.PrintingUtil;

public class MinInsertionsForPalindrome {
	
	@Test
	public void testSuccess() {
		String test = "ab";
		
		int minInsertions;
		
		System.out.println(test);
		minInsertions = getMinInsertions(test);
		System.out.println(minInsertions);
		
		
		test = "aa";
		System.out.println(test);
		minInsertions = getMinInsertions(test);
		System.out.println(minInsertions);
		
		test = "abcd";
		System.out.println(test);
		minInsertions = getMinInsertions(test);
		System.out.println(minInsertions);
		
		test = "abcda";
		System.out.println(test);
		minInsertions = getMinInsertions(test);
		System.out.println(minInsertions);
		
		test = "abcde";
		System.out.println(test);
		minInsertions = getMinInsertions(test);
		System.out.println(minInsertions);
	}

	private int getMinInsertions(String str) {
		
		// create a 2d matrix.
		int[][] a = new int[str.length()][str.length()];
		
		// fill diagonal.
		for(int i = 0; i < str.length();i++) {
			a[i][i] = 0;
		}
		
		for(int c = 1; c < str.length(); c++) {
			for(int r = c - 1; r >= 0; r--) {
				if(str.charAt(r)  == str.charAt(c)) {
					a[r][c] =   a[r+1][c-1];
				}
				else {
					a[r][c] = Math.min( a[r+1][c], a[r][c-1]) + 1;
				}
//				System.out.println(str.charAt(r)+ ":" + str.charAt(c));
//				System.out.println(r + ":" + c);
//				PrintingUtil.printTwoDArray(a);
			}
		}
		
		return a[0][str.length()-1];
	}

}
