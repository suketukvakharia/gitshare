package dynamicProgramming;

import org.junit.Test;

/**
 * Count all possible paths going from top left to bottom right.
 * http://www.geeksforgeeks.org/count-possible-paths-top-left-bottom-right-nxm-matrix/
 * Can only move down or right.
 * @author suketu
 *
 */
public class CountPossiblePathsMatrix {

	@Test
	public void testSuccess() {
		int possiblePaths = 0, n = 3;
		
		possiblePaths = getNumberOfPaths(0,0,n);
		System.out.println(possiblePaths);
		
		n = 4;
		possiblePaths = getNumberOfPaths(0,0,n);
		System.out.println(possiblePaths);
		
		n = 5;
		possiblePaths = getNumberOfPaths(0,0,n);
		System.out.println(possiblePaths);
		
		n = 6;
		possiblePaths = getNumberOfPaths(0,0,n);
		System.out.println(possiblePaths);
	}

	private int getNumberOfPaths(int x, int y, int n) {
		int[][] count = new int[n][n];
		return getNumberOfPaths(x, y, n-1, count);
	}

	private int getNumberOfPaths(int x, int y, int n, int[][] count) {
		if(x == n && y == n) return 1;
		
		if(count[x][y] != 0) return count[x][y];
		
		int rightPaths = x + 1 <= n ? getNumberOfPaths(x + 1, y, n, count) : 0;
		
		int bottompaths = y + 1 <= n ? getNumberOfPaths(x, y+1, n, count): 0;
		
		count[x][y] = rightPaths + bottompaths;
		return count[x][y];
	}
}
