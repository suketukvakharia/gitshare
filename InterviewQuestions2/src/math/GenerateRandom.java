package math;

import java.util.Arrays;
import java.util.Random;

import org.junit.Test;

/**
 * Generate random number.
 * 
 * Research: 
 * http://www.javamex.com/tutorials/random_numbers/java_util_random_algorithm.shtml#.VJT0l14Ag
 * 
 * Always take the largest bit when generating random numbers using LCG:
 * http://www.javamex.com/tutorials/random_numbers/lcg_bit_positions.shtml
 * 
 * LCS is basically this:
 * 
 * seed = (seed * multiplier) + constant;
 * bounded values can be taken by doing seed%n. Additionally you can try and do a shift to get the more random bits.
 * @author suketu
 *
 */
public class GenerateRandom {
    
    
    @Test 
    public void testXOR(){
        
        int[] frequency = new int[32];
        for(int i = 0; i < 1000; i++) {
            
            frequency[next(2)]++;
        }
        
        System.out.println(Arrays.toString(frequency));
    }
    
    protected int next(int nbits) {
        // N.B. Not thread-safe!
        long x = System.nanoTime();
        x ^= (x << 21);
        x ^= (x >>> 35);
        x ^= (x << 4);
        x &= ((1L << nbits) -1);
        return (int) x;
      }
    
    @Test
    public void testLibrary() {
        Random rand = new Random();
        int n = 10;
        for(int i = 0; i < n; i++) {
            System.out.println(rand.nextInt());
        }
    }
    
    
    @Test
    public void testOurs() {
        generateInt(10);
    }
    
    private void generateIntWithShift(int n) {
        
    }
    
    private void generateInt(int n) {
        int seed = (int)System.nanoTime();
        int multiplier = 437898495;
        int append = 301;
        int mask = 42545;
        
        for(int i = 0; i < n+10; i++) {
            
            seed = seed * multiplier + append;
            System.out.println(Math.abs(seed % n));
        }
    }

    @Test
    public void testPostedRandom() {
        generatePostedInt(10);
    }
    
    public void generatePostedInt(int num){
        int seed = num;
        for(int i=1;i<num;i++){
            long nanoTime = System.nanoTime();
            System.out.println("NanoTime:" + nanoTime);
            System.out.println("NanoTime:" + ((int)nanoTime));
            seed = ((int)nanoTime%(i));
            if(seed<=0)
                seed = ((int)System.currentTimeMillis()%i);

            System.out.println(seed);
        }
    }

}
