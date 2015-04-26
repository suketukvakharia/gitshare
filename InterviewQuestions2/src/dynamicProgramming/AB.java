package dynamicProgramming;

import org.junit.Test;

public class AB {

	@Test
	public void testSuccess() {
		
		System.out.println("Testing");
		
		int n, k;
		String result;
		
		n = 1;
		k = 0;
		result = createString(n, k);
		System.out.println("n is:" + n + " k is:" + k);
		System.out.println(result);
		
		n = 2;
		k = 1;
		result = createString(n, k);
		System.out.println("n is:" + n + " k is:" + k);
		System.out.println(result);
		
		
		n = 10;
		k = 12;
		result = createString(n, k);
		System.out.println("n is:" + n + " k is:" + k);
		System.out.println(result);
		
	}
	
	/**
	 * At each point we have a choice of A or B.
	 * 2 ^ N combinations/permutations to try.
	 * 
	 * 
	 * @param N
	 * @param K
	 * @return
	 */
	public String createString(int N, int K ) {
		
		
		int totalAs = 0;
		int currentN = 0;
		
		int i = 0;
		boolean[] choice = new boolean[N];
		
		return createString(N,K, choice, i, totalAs, currentN);
	}

	private String createString(int n, int k, boolean[] choices, int i,
			int totalAs, int currentK) {
		
		if(currentK == k) {
			// set next choices to true, i.e. all A's and return.
			for(int j = i; j < choices.length; j++) {
				choices[j] = true;
			}
			return boolToString(choices);
		}
		
		if(i >= n) {
			return "";
		}
		
		// make the two choices and be done with it.
		choices[i] = true;
		String trueResult = createString(n, k, choices, i+1, totalAs + 1, currentK);
		
		if(trueResult.length() > 0) {
			return trueResult;
		}
		choices[i] = false;
		return createString(n, k, choices, i+1, totalAs, currentK + totalAs);
	}

	private String boolToString(boolean[] choices) {
		
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < choices.length; i++) {
			sb.append(choices[i] ? 'A' : 'B');
		}
		return sb.toString();
	}
}
