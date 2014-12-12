package math;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

public class FindPrimes {
    
    
    @Test 
    public void testSuccess() {
        
        int[] primes = findPrimes(1500);
        
        System.out.println(Arrays.toString(primes));
    }
    
    
    public int[] findPrimes(int goUpto) {
        
        List<Integer> primes = new LinkedList<Integer>();
        List<Integer> primeCount = new LinkedList<Integer>();
        
        primes.add(2);
        primeCount.add(2);
        
        
        int sieveStartAt = 3, endAt = sieveStartAt+1000;
        // create sieves and update primes as needed.
        while(sieveStartAt < goUpto) {
            
            boolean[] sieve = new boolean[endAt - sieveStartAt];
            
            // go through each prime and mark the sieve entries that can be reached by marking them as true.
            for(int i = 0; i < primes.size(); i++) {
                sieveOutMultiplesForPrime(sieve, sieveStartAt, endAt, primes.get(i), primeCount, i);
            }
            
            // add new primes
            for(int i = 0; i < sieve.length; i++) {
                if(!sieve[i]) {
                    primes.add(i + sieveStartAt);
                    primeCount.add(i + sieveStartAt);
                    sieveOutMultiplesForPrime(sieve, sieveStartAt, endAt, primes.get(primes.size()-1), primeCount, primes.size()-1);
                }
            }
            
            sieveStartAt = endAt+1;
            endAt = (sieveStartAt + 1000 > goUpto) ? goUpto : sieveStartAt + 1000;
            
        }
        
        int[] primesArr = new int[primes.size()];
        for(int i = 0; i < primes.size(); i++) 
            primesArr[i] = primes.get(i);
        
        return primesArr;
    }

    private void sieveOutMultiplesForPrime(boolean[] sieve, int sieveStartAt,
            int endAt, int prime, List<Integer> primeCountList, int index) {
        
        int primeCount = primeCountList.get(index);
        for(;primeCount < endAt; primeCount += prime) {
            if(primeCount < sieveStartAt) continue;
            
            sieve[primeCount - sieveStartAt] = true;
        }
        primeCountList.set(index, primeCount);
    }

}
