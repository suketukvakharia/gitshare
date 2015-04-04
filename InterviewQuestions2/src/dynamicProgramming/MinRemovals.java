package dynamicProgramming;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Test;


/**
 * http://www.geeksforgeeks.org/remove-minimum-elements-either-side-2min-max/
 * 
 * @author suketu
 *
 */
public class MinRemovals {

	@Test
	public void testSuccess() {
		int[] a = {4, 5, 100, 9, 10, 11, 12, 15, 200};
		int minRemovals = -1;
		
		minRemovals = getMinRemovals(a);
		System.out.println(minRemovals);
		
		int[] temp = {4, 7, 5, 6};
		a = temp;
		minRemovals = getMinRemovals(a);
		System.out.println(minRemovals);
		
		int [] temp2= {20, 7, 5, 6};
		a = temp2;
		minRemovals = getMinRemovals(a);
		System.out.println(minRemovals);
		
		int[] temp3 = {20, 4, 1, 3};
		a = temp3;
		minRemovals = getMinRemovals(a);
		System.out.println(minRemovals);
	}

	/**
	 * Sorting would be O(n * log(N)).
	 * The recursive call is evaluated for each (ast, aend) pair possible.
	 * There are O(n^2) such pairs possible. (i.e. n + n-1 + n-2 .... 1 ==> O(n^2).
	 * 
	 * The operations for each (ast,aend) pairs are constant:
	 * call for two new (ast, aend) pairs.
	 * trim the bst and bend pointers if needed. (Note there should only be be one trimming on average for each ast,aend pair.
	 * 
	 * This gives us total of: O(n * logN) + O(n^2) * C
	 * This should be O(n^2) solution.
	 * @param a
	 * @return
	 */
	private int getMinRemovals(int[] a) {
		int[] sortedA = Arrays.copyOf(a, a.length);
		Arrays.sort(sortedA);
		Set<Integer> extractedIntegers = new HashSet<Integer>();
		Map<String, Integer> minimumCachedPath = new HashMap<String, Integer>();
		return getMinRemovals(a, 0, a.length-1, sortedA, 0, sortedA.length-1, extractedIntegers, minimumCachedPath);
	}

	private int getMinRemovals(int[] a, int ast, int aend, int[] b, int bst,
			int bend, Set<Integer> extractedIntegers, Map<String, Integer> minimumCachedPath) {
		if(ast > aend || bst > bend) return Integer.MAX_VALUE; // if ast is larger than aend
		
		if(minimumCachedPath.containsKey(ast+":" + aend)) {
			return minimumCachedPath.get(ast + ":" + aend);
		}
		
		// adjust bst and bend as needed. 
		while(extractedIntegers.contains(b[bst])) bst++;
		while(extractedIntegers.contains(b[bend])) bend--;
		
		if(b[bst] * 2 > b[bend]) {
			return ast + a.length - aend - 1;
		}
		
		// take one off of the front of a
		extractedIntegers.add(a[ast++]);
		int leftMin = getMinRemovals(a, ast, aend, b, bst, bend, extractedIntegers, minimumCachedPath);
		extractedIntegers.remove(a[--ast]);
		
		extractedIntegers.add(a[aend--]);
		int rightMin = getMinRemovals(a, ast, aend, b, bst, bend, extractedIntegers, minimumCachedPath);
		extractedIntegers.remove(a[++aend]);
		
		int min = Math.min(leftMin, rightMin);
		minimumCachedPath.put(ast+":" + aend, min);
		
		return min;
	}
}
