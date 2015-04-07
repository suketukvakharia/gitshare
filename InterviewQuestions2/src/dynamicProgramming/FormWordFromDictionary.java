package dynamicProgramming;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

public class FormWordFromDictionary {
	
	
	@Test
	public void testSuccess() {
		
		String largeWord = "largeWordssss";
		
		String[] arr = {"large", "Words", "Word", "s"};
		Set<String> dictionary = getDict(arr);
		
		boolean possible = canFormWord(dictionary, largeWord);
		System.out.println(possible);
	}
	
	private Set<String> getDict(String[] arr) {
		Set<String> dictionary = new HashSet<String>();
		for(String element: arr) {
			dictionary.add(element);
		}
		return dictionary;
	}

	public boolean canFormWord(Set<String> dictionary, String largeWord) {
		
		int[] possible = new int[largeWord.length()];
		int st = 0;
		
		return canFormWord(dictionary, largeWord, possible, st);
	}

	private boolean canFormWord(Set<String> dictionary, String largeWord,
			int[] possible, int st) {
		if(st >= largeWord.length()) return true;
		
		if(st != 0 && possible[st-1] == -1) return false;
		
		for(int i = st + 1; i <= largeWord.length(); i++) {
			if(dictionary.contains(largeWord.substring(st, i))) {
				boolean currentPossible = canFormWord(dictionary, largeWord, possible, i);
				if(currentPossible) return true;
				else possible[i -1] = -1;
			}
		}
		return false;
	}

}
