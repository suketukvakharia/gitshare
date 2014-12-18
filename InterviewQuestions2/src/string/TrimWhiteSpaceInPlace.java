package string;

import org.junit.Test;


/**
 * http://www.careercup.com/question?id=5178446623801344
 * @author suketu
 *
 */
public class TrimWhiteSpaceInPlace {

    
    @Test
    public void testSuccess() {
        
        String arg = "   Hello   hello  dkjkjls          HELLO  ";
        char[] argCharArr = arg.toCharArray();
        
        System.out.println(argCharArr);
        trimWhiteSpaceInPlace(argCharArr);
        System.out.println(argCharArr);
        
    }
    
    public void trimWhiteSpaceInPlace(char[] arg) {
        
        if(arg == null || arg.length <= 1) return;
        
        
        int nextNonEmptyStartAt = 0;
        for(int i = 0; i < arg.length; i++) {
            
            char current = arg[i];
            if(!Character.isWhitespace(current)) {
                
                for(int j = i; j < arg.length && !Character.isWhitespace(arg[j]); j++,nextNonEmptyStartAt++) {
                    swap(arg,j, nextNonEmptyStartAt);
                }
                i = nextNonEmptyStartAt;
                nextNonEmptyStartAt++;
            }
            
        }
    }

    private void swap(char[] arg, int j, int nextNonEmptyStartAt) {
        char temp = arg[j];
        arg[j] = arg[nextNonEmptyStartAt];
        arg[nextNonEmptyStartAt] = temp;
    }
}
