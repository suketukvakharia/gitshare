package permutations;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;



/**
 * http://www.careercup.com/question?id=5196192088588288
 * @author suketu
 *
 */
public class PirntSubsets {

	@Test
	public void testSuccess() {
		
		printSubsets(3, 2);
	}
	
	
	/**
	 * Two ways, iterate through your choices and swap and unswap?
	 * Or keep a hash set of your choices and iterate through 1 to n.
	 * 
	 * I choose to do the choices way better that way.
	 * @param n
	 * @param k
	 */
	public void printSubsets(int n, int k) {
		
		Integer[] choices = new Integer[n];
		for(int i = 1; i <= n; i++) choices[i-1] = i;
		
		printCombinations(choices, k, 0, new HashSet<Integer>());
	}

	private void printCombinations(Integer[] choices, int k, int i, Set<Integer> combination) {
		if(combination.size() == k) {
			for(Integer integer: combination) {
				System.out.print(integer + " ,");
			}
			System.out.println("");
			return;
		}
		
		if(i == choices.length) {
			return;
		}
		
		// current choice not in combination
		printCombinations(choices, k, i + 1, combination);
		
		// current choice in combinations
		combination.add(choices[i]);
		printCombinations(choices, k, i+1, combination);
		combination.remove(choices[i]);
	}
}
