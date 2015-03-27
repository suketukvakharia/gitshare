package dynamicProgramming;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Test;


/**
 * http://www.careercup.com/question?id=5637944224251904
 * @author suketu
 *
 */
public class GetMaxBySwappingDigits {

    
    @Test
    public void testSuccess() {
        int arg = 201;
        int k = 2;
        
        System.out.println(getMaxBySwappingDigits(arg, 2));
        System.out.println(getMaxSwappingDigits2(intToArr(arg), 2));

        
        arg = 132;
        k = 1;
        System.out.println(getMaxBySwappingDigits(arg, k));
        System.out.println(getMaxSwappingDigits2(intToArr(arg), k));
        
        arg = 132;
        k = 2;
        System.out.println(getMaxBySwappingDigits(arg, k));
        System.out.println(getMaxSwappingDigits2(intToArr(arg), k));
        
        arg = 7899;
        k = 2;
        System.out.println(getMaxBySwappingDigits(arg, k));
        System.out.println(getMaxSwappingDigits2(intToArr(arg), k));

        arg = 8799;
        k = 2;
        System.out.println(getMaxBySwappingDigits(arg, k));
        System.out.println(getMaxSwappingDigits2(intToArr(arg), k));
        
        arg = 87199;
        k = 2;
        System.out.println(getMaxBySwappingDigits(arg, k));
        System.out.println(getMaxSwappingDigits2(intToArr(arg), k));

    }
    
    
    
    public static int getMaxBySwappingDigits(int arg, int maxSwaps) {
        
        
        int[] argArr = intToArr(arg);
        System.out.println(Arrays.toString(argArr));
        
        Set<String> badPaths = new HashSet<>();
        
        return getMaxBySwappingDigits(argArr, maxSwaps, badPaths, 0);
        
    }
    
    private static int getMaxSwappingDigits2(int[] argArr, int maxSwaps) {
    	return getMaxSwappingDigits2(argArr, maxSwaps, 0, arrToInt(argArr));
    }
    
    private static int getMaxSwappingDigits2(int[] argArr, int maxSwaps, int start, int maxSoFar) {
		if(maxSwaps == 0) {
			return arrToInt(argArr) > maxSoFar ? arrToInt(argArr) : maxSoFar;
		}
		
		for(int i = start; i < argArr.length; i++) {
			for(int j = i+1; j < argArr.length; j++) {
				// skip if not larger.
				if(argArr[j] < argArr[i]) continue;
				
				// swap update max and reiterate
				swap(argArr, i, j);
				int temp = arrToInt(argArr);
				if(temp > maxSoFar) maxSoFar = temp;
				maxSoFar = getMaxSwappingDigits2(argArr, maxSwaps-1, i+1, maxSoFar);
				swap(argArr, i, j);
			}
		}
		return maxSoFar;
	}



	private static int getMaxBySwappingDigits(int[] argArr, int maxSwaps,
            Set<String> visitedPaths, int maxSoFar) {
        
        if(maxSwaps == 0) {
            int curMax = arrToInt(argArr);
            return Math.max(curMax, maxSwaps);
        }
        
        for(int i = 0; i < argArr.length; i++) {
            
            for(int j = i; j < argArr.length; j++) {
                
                swap(argArr, i, j);
                String visitedKey = "" + arrToInt(argArr) + ":" + maxSwaps;
                
                // see if this is larger than maxSoFar
                if(!visitedPaths.contains(visitedKey)) {
                    int subPathMax = getMaxBySwappingDigits(argArr, maxSwaps-1, visitedPaths, maxSoFar);
                    
                    maxSoFar = Math.max(subPathMax, maxSoFar);
                    // mark visited
                    visitedPaths.add(visitedKey);
                }
                
                // unswap
                swap(argArr, i, j);
            }
        }
        
        return maxSoFar;
    }

    private static int arrToInt(int[] argArr) {
        int toReturn = 0, scale = 1;
        for(int i = argArr.length-1; i >=0; i--) {
            toReturn += scale * argArr[i];
            scale *= 10;
        }
        return toReturn;
    }

    private static void swap(int[] argArr, int i, int j) {
        int temp = argArr[i];
        argArr[i] = argArr[j];
        argArr[j] = temp;
    }

    private static int[] intToArr(int arg) {
        int sizeNeeded = 0, temp = arg;
        
        while(temp > 0) {
            temp/=10;
            sizeNeeded++;
        }
        int[] argArr = new int[sizeNeeded];
        temp = arg;
        for(int i = argArr.length-1; i >= 0; i--) {
            argArr[i] = temp%10;
            temp = temp/10;
        }
        
        return argArr;
    }
}
