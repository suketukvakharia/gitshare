package dynamicProgramming;

import org.junit.Test;

import util.PrintingUtil;

/***
 * Question: This is based upon the question: 
 * http://www.geeksforgeeks.org/dynamic-programming-set-37-boolean-parenthesization-problem/
 * @author suketu
 *
 */
public class ParenthesizationProblem {
	
	@Test
	public void testSuccess() {
		boolean symbols[] = {true, true, false, true};
	    char operators[] = "|&^".toCharArray();
	 
	    // There are 4 ways
	    // ((T|T)&(F^T)), (T|(T&(F^T))), (((T|T)&F)^T) and (T|((T&F)^T))
	    System.out.println(countParenth(symbols, operators));
	    
	}
	
	/**
	 * This is based upon the question: 
	 * http://www.geeksforgeeks.org/dynamic-programming-set-37-boolean-parenthesization-problem/
	 * 
	 * The mathematical formulation for True and False values being:
	 * 
	 * T(i,j) is:
	 * Sum over all k value i through j-1
	 * if operators[k] is '|' Total(i,k) * Total(k+1,j) - F(i,k) * F(k+1, j)
	 * if operators[k] is '&' T(i,k) * T(k+1,j)
	 * if operators[k] is '^' F(i,k) * T(k+1,j) + T(i,k) * F(k+1,j)
	 * 
	 * F(i,j) is similarly:
	 * if operator[k] is '|' F(i,k) + F(k+1,j)
	 * if operator[k] is '&' Total(i,k) * Total(k+1,j) - T(i,k) * T(k+1,j)
	 * if operator[k] is '^' F(i,k) * F(k+1,j) + T(i,k) + T(k+1,j)
	 * 
	 * @param symbols
	 * @param operators
	 * @return
	 */
	public int countParenth(boolean[] symbols, char[] operators) {
		
		int[][] T = new int[symbols.length][symbols.length], F = new int[symbols.length][symbols.length];
		
		for(int i = 0; i < symbols.length; i++) {
			T[i][i] = symbols[i] ? 1:0;
			F[i][i] = symbols[i] ? 0:1;
		}
		
		// gap goes from 1 to n
		// i goes from 0 to n
		// j is i + gap
		// k goes from i through j - 1
		
		for(int gap = 1; gap < symbols.length; gap++) {
			for(int i = 0; i < symbols.length; i++) {
				int j = i + gap;
				for(int k = i; k < j && k < symbols.length && j < symbols.length; k++) {
					
					int totalIK = T[i][k] + F[i][k];
					int totalKJ = T[k+1][j] + F[k+1][j];

					if(operators[k] == '&') {
						System.out.println("AND");
						T[i][j] += T[i][k] * T[k+1][j];
						F[i][j] += totalIK * totalKJ - T[i][k] * T[k+1][j];
					}
					else if(operators[k] == '|') {
						System.out.println("OR");
						T[i][j] += totalIK * totalKJ - F[i][k] * F[k+1][j];
						F[i][j] += F[i][k] * F[k+1][j];
					}
					else if(operators[k] == '^') {
						System.out.println("XOR");
						T[i][j] += T[i][k] * F[k+1][j] + F[i][k] * T[k+1][j];
						F[i][j] += F[i][k] * F[k+1][j] + T[i][k] * T[k+1][j];
					}
				}
			}
		}
		PrintingUtil.printTwoDArray(T, "T");
		PrintingUtil.printTwoDArray(F, "F");
		
		// now use the formula to iterate over and fill values.
		return T[0][symbols.length-1];
	}

}
