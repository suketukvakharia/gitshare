package math;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.junit.Test;

public class PrintDivisionDecimal {
    
    @Test
    public void testSuccess() {
        
        int n = 1, d = 3;
        
        System.out.println("Testing n:" + n + ", d:" + d);
        printRationalDivisitonResult(n, d);
        
        n = 2;
        d = 4;
        System.out.println("Testing n:" + n + ", d:" + d);
        printRationalDivisitonResult(n, d);
        
        n = 22;
        d = 4;
        System.out.println("Testing n:" + n + ", d:" + d);
        printRationalDivisitonResult(n, d);
        
        n = 22;
        d = 7;
        System.out.println("Testing n:" + n + ", d:" + d);
        printRationalDivisitonResult(n, d);
        
        n = 58;
        d = 2828;
        System.out.println("Testing n:" + n + ", d:" + d);
        printRationalDivisitonResult(n, d);
    }

    private void printRationalDivisitonResult(int n, int d) {
        
        int beforeDecimal = n / d;
        
        int currentRem = n % d;
        
        List<Integer> afterDecimal = new ArrayList<Integer>();
        Map<Integer, Integer> remainderToDecimalPlace = new HashMap<Integer, Integer>();
        int repeatPlace = -1;
        int i = 0;
        while(true) {
            currentRem*=10;
            int currentVal = currentRem/ d;
            currentRem = currentRem % d;
            
            if(remainderToDecimalPlace.containsKey(currentRem)) {
                repeatPlace = remainderToDecimalPlace.get(currentRem);
                break;
            } else {
                remainderToDecimalPlace.put(currentRem, i);
                afterDecimal.add(currentVal);
            }
            if(currentRem == 0) {
                repeatPlace = i;
                break;
            }
            i++;
        }
        System.out.print(beforeDecimal + ".");
        for(int j = 0; j < afterDecimal.size(); j++) {
            if(j == repeatPlace) System.out.print("(");
            System.out.print(afterDecimal.get(j));
        }
        System.out.println(")");
    }
    

}
