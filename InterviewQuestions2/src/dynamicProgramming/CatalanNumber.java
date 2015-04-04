package dynamicProgramming;

import java.math.BigInteger;

import org.junit.Test;


/**
 * Apparently Catalan numbers are pretty big in combinatorial mathematics.
 * 
 * http://en.wikipedia.org/wiki/Catalan_number
 * 
 * http://www.geeksforgeeks.org/program-nth-catalan-number/
 * 
 * @author suketu
 *
 */
public class CatalanNumber {

	
	@Test
	public void testSuccess() {
		int n = 4;
		int catalanNumber;
		
		catalanNumber = getCatalanDirectly(n);
		System.out.println(catalanNumber);
		
		n = 10;
		catalanNumber = getCatalanDirectly(n);
		System.out.println(catalanNumber);
		
		n = 15;
		catalanNumber = getCatalanDirectly(n);
		System.out.println(catalanNumber);
		
		// TODO Look into recursive solution at a later time if you have some time.
	}

	private int getCatalanDirectly(int n) {
		
		return factorial(2 *n).divide(factorial(n).multiply(factorial(n+1))).intValue();
	}

	private BigInteger factorial(int i) {
		BigInteger factValue = BigInteger.ONE;
		
		for(int j = 1; j <= i; j++) {
			factValue = factValue.multiply(BigInteger.valueOf(j));
		}
		System.out.println("Fact value:" + factValue + " i value is:" + i);
		return factValue;
	}
	
	
}
