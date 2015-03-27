package dynamicProgramming;

import org.junit.Assert;
import org.junit.Test;


/**
 * The problem statement is given a set of coins and a change amount to generate what would be the minimum number of coins needed.
 * 
 * @author suketu
 *
 */
public class GenerateChange {

	@Test
	public void testSuccess() {
		
		int[] coins =  {1, 5, 8, 25};
		
		int changeAmount;
		
		changeAmount = 25;
		Assert.assertEquals(1, getNumberOfCoinsNeeded(changeAmount, coins));
		
		changeAmount = 1;
		Assert.assertEquals(1, getNumberOfCoinsNeeded(changeAmount, coins));
		
		// 2 should be 2
		changeAmount = 2;
		Assert.assertEquals(2, getNumberOfCoinsNeeded(changeAmount, coins));
		
		changeAmount = 0;
		Assert.assertEquals(0, getNumberOfCoinsNeeded(changeAmount, coins));
		
		changeAmount = 8;
		Assert.assertEquals(1, getNumberOfCoinsNeeded(changeAmount, coins));
		
		changeAmount = 11;
		Assert.assertEquals(3, getNumberOfCoinsNeeded(changeAmount, coins));
		
		changeAmount = 14;
		Assert.assertEquals(3, getNumberOfCoinsNeeded(changeAmount, coins));
	}
	
	public int getNumberOfCoinsNeeded(int changeAmount, int[] coins) {
		
		int coinsNeeded[] = new int[changeAmount+1];
		
		for(int i = 0; i < coinsNeeded.length; i++) {
			coinsNeeded[i] = i;
		}
		
		for(int i = 1; i <= changeAmount; i++) {
			for (int coin : coins) {
				
				int costWithThisCoin = i / coin + coinsNeeded[i % coin];
				coinsNeeded[i] = Math.min(coinsNeeded[i], costWithThisCoin);
			}
		}
		
		return coinsNeeded[changeAmount];
	}
}
