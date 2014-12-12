package math;

import org.junit.Test;

/**
 * Solve this questin: http://www.careercup.com/question?id=5680043955060736
 * @author suketu
 *
 */
public class PrintNumbersLexicographically {
    
    @Test
    public void testSuccess() {
        printNumbersLexicographically(1000);
    }
    
    public static void printNumbersLexicographically(int max) {
        int toPrint = 0;
        
        for(int i = 1; i <= max; i++) {
            // to find next see if we can add 0 at the end. 
            if(toPrint != 0 && toPrint*10 <= max) {
                toPrint *=10;
            }
            else {
                // increment the smallest digit
                while(true) {
                    if(toPrint % 10 != 9 && toPrint < max) {
                        toPrint++;
                        break;
                    }
                    toPrint /=10;
                }
            }
            System.out.println(toPrint);
        }
    }
}
