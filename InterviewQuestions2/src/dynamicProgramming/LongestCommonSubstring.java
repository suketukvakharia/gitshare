package dynamicProgramming;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;


/**
 * Longest common subsctring is a pretty common dynamic programming problem. 
 * 
 * http://www.geeksforgeeks.org/longest-common-substring/
 * 
 * Can be done is O(m) using a rotating hash type solution. The algorithm is called robin karp algorithm. 
 * 
 * 
 * @author suketu
 *
 */
public class LongestCommonSubstring {

	@Test
	public void testSuccess(){
		String str = "ABC ABCDAB ABCDABCDABDE", subStr = "ABCDABD";
		
		Assert.assertTrue(kmpSubstringSearch(str, subStr));
	}
	
	public boolean kmpSubstringSearch(String str, String subStr) {
		
		if(str == null || subStr == null || str.length() < subStr.length() || subStr.length() == 0) {
			return false;
		}
		int[] retraceIndex = new int[subStr.length()];
		
		retraceIndex[0] = -1;
		for(int i = 1, j = 0; i < retraceIndex.length; i++) {
			retraceIndex[i] = j;
			if(subStr.charAt(i) == subStr.charAt(j)) {
				j++;
			}
			else {
				j = 0;
			}
		}
		System.out.println("retraceArray:" + Arrays.toString(retraceIndex));
		
		// now loop through larger.
		int i = 0;
		int j = 0;
		while(i < str.length()) {
			if(str.charAt(i) == subStr.charAt(j)) {
				if(++j == subStr.length()) {
					return true;
				}
				i++;
			}
			else {
				j = retraceIndex[j];
				if(j == -1) {
					j = 0;
					i++;
				}
			}
		}
		
		return false;
	}
}
