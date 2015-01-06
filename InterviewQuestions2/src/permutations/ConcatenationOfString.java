package permutations;

import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

public class ConcatenationOfString {
    
    @Test
    public void testSuccess() {
        Set<String> dict = new HashSet<String>();
        dict.add("hello");
        dict.add("world");
        dict.add("super");
        dict.add("hell");
        
        Assert.assertTrue(isWordPossible("helloworld", dict));
        Assert.assertFalse(isWordPossible("superman", dict));
        Assert.assertTrue(isWordPossible("hellohello", dict));
    }
    
    
    public boolean isWordPossible(String word, Set<String> dictionary) {
        
        
        return isWordPossible(word, dictionary, 0);
    }

    private boolean isWordPossible(String word, Set<String> dictionary, int startAt) {
        
        for(int i = startAt+1; i <= word.length(); i++) {
            
            if(dictionary.contains(word.substring(startAt,i))) {
                if(isWordPossible(word, dictionary, i)  || i == word.length() ) {
                    return true;
                }
            }
        }
        return false;
    }

}
