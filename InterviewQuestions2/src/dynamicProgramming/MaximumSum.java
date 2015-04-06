package dynamicProgramming;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;



/**
 * http://www.geeksforgeeks.org/dynamic-programming-set-36-cut-a-rope-to-maximize-product/
 * Given a rope of length n meters, cut the rope in different parts of integer lengths 
 * in a way that maximizes product of lengths of all parts. 
 * You must make at least one cut. 
 * Assume that the length of rope is more than 2 meters.
 * @author suketu
 *
 */
public class MaximumSum {

	@Test
	public void testSuccess() {
		int n;
		int maxPossible;
		
		for(n = 3; n <= 10; n++) {
			maxPossible = maximumProduct(n);
			System.out.println(maxPossible);
		}
		
		for(n = 3; n <= 10; n++) {
			maxPossible = maxProd(n);
			System.out.println(maxPossible);
		}
	}
	
	public int maximumProduct(int n) {
		if(n <= 2) return n;
		Map<Integer, Integer> cachedMax = new HashMap<Integer, Integer>();
		return maximumProduct(n, cachedMax);
	}
	
	
	/**
	 * This one is a method I copied.
	 * Built on the idea/observation that maximum product sum has most amount of 3 then 4 for larger values.
	 * This solution exploits that fact.
	 * @param n
	 * @return
	 */
	private int maxProd(int n)
	{
	   // n equals to 2 or 3 must be handled explicitly
	   if (n == 2 || n == 3) return (n-1);
	 
	   // Keep removing parts of size 3 while n is greater than 4
	   int res = 1;
	   while (n > 4)
	   {
	       n -= 3;
	       res *= 3; // Keep multiplying 3 to res
	   }
	   return (n * res); // The last part multiplied by previous parts
	}

	/**
	 * Note might have been better to have just used and integer array to store cached max values.
	 * Keep this in mind whenever using Map<Integer, Integer> often there is an optimized space things
	 * that can used a simple 1D array. 
	 * Additionally in those cases you can write a non-recursive iterative solution to the problem as well.
	 * @param n
	 * @param cachedMax
	 * @return
	 */
	private int maximumProduct(int n, Map<Integer, Integer> cachedMax) {
		if(n <= 2) return n;
		if(cachedMax.containsKey(n)) return cachedMax.get(n);
		
		// split it all ways possible.
		int maxPossible = Integer.MIN_VALUE;
		for(int i = 1; i <= n/2; i++) {
			int left = maximumProduct(n-i, cachedMax);
			left = left > n-i ? left : n-i;
			int right =  maximumProduct(i, cachedMax);
			right = right > i ? right : i;
			int iterationMax = left * right;
			if(iterationMax > maxPossible) {
				maxPossible = iterationMax;
			}
		}
		cachedMax.put(n, maxPossible);
		
		return maxPossible;
	}
}
