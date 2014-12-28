package permutations;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Code chef question:
 * http://www.codechef.com/problems/BYTEISLE
 * 
 * @author suketu
 *
 */
public final class Byteknights {
    
    /**
     * Reads code chef input!!
     * Ignores first line.
     * @return
     * @throws Exception 
     * @throws NumberFormatException 
     */
    public static List<String> readInput() throws NumberFormatException, Exception {
        
        List<String> toReturn = new ArrayList<String>();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//        BufferedReader br = new BufferedReader(new FileReader(new File("f:\\var\\tmp\\in.txt")));
        
        String readLine = br.readLine();
        int numberOfTestCases = Integer.valueOf(readLine);
        toReturn.add(readLine);
        for(int i = 0; i < numberOfTestCases; i++) {
            readTestCase(toReturn,br);
        }
        
        return toReturn;
    }
    
    private static void readTestCase(List<String> toReturn, BufferedReader br) throws IOException {
        String readLine = br.readLine();
        int testLines = Integer.valueOf(readLine);
        
        toReturn.add(readLine);
        for(int i = 0; i < testLines; i++) {
            toReturn.add(br.readLine());
        }
    }
    
    public static void main(String[] args) throws Exception {
        
        List<String> toProcess = readInput();
//        List<String> toProcess = staticInput();
        
        int numberOfTests = Integer.valueOf(toProcess.get(0));
        
        int j = 1;
        for(int i = 0; i < numberOfTests; i++) {
            // process each test 
            int testCaseLines = Integer.valueOf(toProcess.get(j++));
            int[][] intervals = new int[testCaseLines][2];
            
            for(int k = 0; k < testCaseLines; k++) {
                String current = toProcess.get(j++);
                
                String[] split = current.split(" ");
                intervals[k][0] = Integer.valueOf(split[0]);
                intervals[k][1] = Integer.valueOf(split[1]);
            }
            
            process(intervals);
            System.out.flush();
        }
    }

    private static List<String> staticInput() {
        List<String> staticInput = Arrays.asList(   "3",
                                                    "1",
                                                    "0 1",
                                                    "4",
                                                    "1 4",
                                                    "2 4",
                                                    "3 4",
                                                    "4 4",
                                                    "3",
                                                    "1 2",
                                                    "0 0",
                                                    "1 3");
        return staticInput;
    }

    private static void process(int[][] intervals) {
        
        int[] intervalContainsCount = new int[intervals.length + 1];
        
        for(int i = 0; i < intervals.length; i++) {
            int from = intervals[i][0];
            int to = intervals[i][1];
            
            if(from < 0) {
                from = 0;
            }
            
            while(from <= to && from < intervalContainsCount.length && from > 0) {
                intervalContainsCount[from++]++;
            }
        }
        
        // number of solutions 
        int numberOfSolutions = 0;
        int smallestSolutionNumber = Integer.MAX_VALUE;
        
        for(int i = 0; i < intervalContainsCount.length; i++) {
            if(intervalContainsCount[i] == i) {
                numberOfSolutions++;
                if(i < smallestSolutionNumber) {
                    smallestSolutionNumber = i;
                }
            }
        }
        
        System.out.println(numberOfSolutions);
        
        for(int i = 0; i < intervals.length; i++) {
            if(smallestSolutionNumber < intervals[i][0] || smallestSolutionNumber > intervals[i][1]) {
                System.out.print("0");
            }
            else {
                System.out.print("1");
            }
        }
        System.out.println();
    }
    

}
