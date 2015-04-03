package dynamicProgramming;

import java.util.Arrays;

import org.junit.Test;


/**
 * Problem is set here:http://www.geeksforgeeks.org/count-number-ways-reach-given-score-game/
 * 
 * 
 * @author suketu
 *
 */
public class NumberOfWaysToGetScore {
	
	@Test
	public void testSuccess() {
		
		long current, timeTook;
		int targetScore = 20;
		int[] scores = {3, 5, 10};
		int numberOfWays;
		
		
		numberOfWays = getNumberOfWaysToScore(targetScore, scores);
		System.out.println("Number of ways:" + numberOfWays);
		
		targetScore = 13;
		numberOfWays = getNumberOfWaysToScore(targetScore, scores);
		System.out.println("Number of ways:" + numberOfWays);
	}

	private int getNumberOfWaysToScore(int targetScore, int[] scores) {
		
		int[] waysCache = new int[targetScore+11];
		waysCache[0] = 1;
		
		for(int i = 0; i <= targetScore; i++) {
			waysCache[i+3] += waysCache[i];
		}
		for(int i = 0; i <= targetScore; i++) {
			waysCache[i+5] += waysCache[i];
		}
		for(int i = 0; i <= targetScore; i++) {
			waysCache[i+10] += waysCache[i];
		}
		return waysCache[targetScore];
	}

	/**
	 * Not the most efficient solution this should be fixed correctly.
	 * @param targetScore
	 * @param scores
	 * @return
	 */
	private int getNumberOfWaysToScoreNaive(int targetScore, int[] scores) {
		if(targetScore < 0) {
			return 0;
		}
		if(targetScore == 0) {
			return 1;
		}
		
		
		int toReturn = 0;
		
		int i = 0;
		for(; i < scores.length && scores[i] == 0; i++);
		
		if(i != scores.length) {
			int tempTargetScore = targetScore;
			int tempScore = scores[i];
			
			// skip using this guy fully:
			scores[i] = 0;
			toReturn += getNumberOfWaysToScoreNaive(tempTargetScore, scores);
			
			// use him at least once, if not more.
			while(tempTargetScore >= 0) {
				tempTargetScore -= tempScore;
				scores[i] = 0;
				toReturn += getNumberOfWaysToScoreNaive(tempTargetScore, scores);
			}
			scores[i] = tempScore;
		}
		
		return toReturn;
	}

}
