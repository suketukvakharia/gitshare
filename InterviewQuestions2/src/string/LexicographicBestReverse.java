package string;

import org.junit.Test;


/**
 * http://www.careercup.com/question?id=5084838654640128
 * @author suketu
 *
 */
public class LexicographicBestReverse {

    @Test 
    public void testSuccess() {
        String arg; 
        
        arg = "abab";
        printBestReverse(arg);
        
        arg = "abba";
        printBestReverse(arg);
        
        arg = "bbaa";
        printBestReverse(arg);
        
        arg = "aaaa";
        printBestReverse(arg);
        
        arg = "babaabba";
        printBestReverse(arg);
    }
    
    public static void printBestReverse(String arg) {
        System.out.println(arg);
        // if a string of a's then don't reverse
        // reverse from the first occurrunce of b
        // ba to ab is good.
        // baa to aab is good.
        // ba and baba NOT
        // babb NOT
        // baab no difference, but baa is better.
        // The problem is simply finding largest positive subsequence where a is 1 and b is -1 after the first b encountered.
        
        int startAt = 0, endAt = 0, currentTally = 0, previousMax = 0;
        
        // mark first b
        int i = 0;
        for(; i < arg.length(); i++) {
            if(arg.charAt(i) == 'b') {
                startAt = i;
                break;
            }
        }
        
        // keep tally and go as far as you can
        for(; i < arg.length(); i++) {
            if(arg.charAt(i) == 'a') {
                currentTally++;
                if(currentTally >= previousMax) {
                    endAt = i;
                }
            }
            else {
                if(currentTally == previousMax) {
                    // b after b initially.
                    if(currentTally != 0) {
                        currentTally = 0;
                        endAt = i -1;
                    }
                }
                else if(currentTally > previousMax) {
                    previousMax = currentTally;
                    currentTally = 0;
                    endAt = i -1;
                }
                else {
                    break;
                }
            }
        }
        
        if(startAt <= endAt) {
            System.out.println("Something found: (" + startAt + ", " + endAt + ")");
        }
        else {
            System.out.println("Something found: (" + 0 + ", " + 0 + ")");
        }
    }
}
