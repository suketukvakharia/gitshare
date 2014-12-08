package math;

import java.util.LinkedList;
import java.util.List;

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
    
    public static void printRationalDivisitonResult(int n, int d) {
        
        // before decimal
        System.out.print(n/d + ".");
        
        int rem = n%d, div = -1;
        List<Integer> remHistory = new LinkedList<>();
        List<Integer> divHistory = new LinkedList<>();
        int repeatFrom = 0;
        
        while(true) {
            rem *= 10;
            div = rem/d;
            if(remHistory.contains(rem)) {
                repeatFrom = remHistory.indexOf(rem);
                break;
            }
            remHistory.add(rem);
            divHistory.add(div);
            rem = rem%d;
        }
        for(int current: divHistory) {
            if(repeatFrom == 0)
                System.out.print("(");
            System.out.print(current);
            repeatFrom--;
        }
        System.out.println(")");
    }

}
