package util;

import java.util.Arrays;

public class PrintingUtil {

	
	public static void printTwoDArray(int [][] arrayToPrint) {
		printTwoDArray(arrayToPrint,"array");
	}

	public static void printTwoDArray(int[][] arrayToPrint, String arrayName) {
		System.out.println("printing array" + arrayName);
		for(int[] subArr: arrayToPrint) {
			System.out.println(Arrays.toString(subArr));
		}
	}
}
