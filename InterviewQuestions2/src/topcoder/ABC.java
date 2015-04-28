package topcoder;


/***
 * Problem description is here:
 * https://arena.topcoder.com/#/u/practiceCode/16320/46381/13645/2/325041
 * 
 * @author suketu
 *
 */
public class ABC {

	
	public String createString(int n, int k) {
		int countA = 0, countB = 0, countC = 0;
		int current = 0;
		int i = 0;
		int[] choices = new int[n];
		
		return createString(n, k, countA, countB, countC, current, choices, i);
	}

	private String createString(int n, int k, int countA, int countB,
			int countC, int current, int[] choices, int i) {
		
		if(k == current) {
			// set rest of choices as A and return
			for(int j = i; j < choices.length; j++) {
				choices[j] = 1;
			}
			return intToString(choices);
		}
		if(i == n) {
			return "";
		}
		
		String result = null;
		// choose A
		choices[i] = 1;
		result = createString(n, k, countA + 1, countB, countC, current, choices, i+1);
		if(result.length() > 0) return result;
		
		// choose B 
		choices[i] = 2;
		result = createString(n, k, countA, countB + 1, countC, current + countA, choices, i+1);
		if(result.length() > 0) return result;
		
		// choose C
		choices[i] = 3;
		result = createString(n, k, countA, countB, countC + 1, current + countA + countB, choices, i+1);
		if(result.length() > 0) return result;
		
		return result;
	}

	private String intToString(int[] choices) {
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < choices.length; i++) {
			if(choices[i] == 1) {
				sb.append('A');
			}
			else if(choices[i] == 2) {
				sb.append('B');
			}
			else {
				sb.append('C');
			}
		}
		return sb.toString();
	}
}
