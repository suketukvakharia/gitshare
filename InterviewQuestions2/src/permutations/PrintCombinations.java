package permutations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;


/**
 * http://www.careercup.com/question?id=5756808635351040
 * @author suketu
 *
 */
public class PrintCombinations {
    
    
    @Test
    public void testSuccess() {
        List<String> list1 = Arrays.asList("quick", "slow"),
                list2 = Arrays.asList("brown","red"),
                list3 = Arrays.asList("fox","dog");
        
        printCombinations(Arrays.asList(list1, list2, list3));
    }
    
    public void printCombinations(List<List<String>> args) {
        
        // setup index for each list
        List<Integer> indexList = new ArrayList<>(args.size());
        for(int i = 0; i < args.size(); i++) indexList.add(0);
        
        boolean done = false;
        while(!done) {
            
            
            // print current combination
            System.out.print("[");
            for(int i = 0; i < args.size(); i++) {
                List<String> arg = args.get(i);
                System.out.print(arg.get(indexList.get(i)) + ", ");
            }
            System.out.println(" ]");
            
            // update the counter.
            for(int i = indexList.size()-1; i >=0; i--) {
                
                int current = indexList.get(i);
                if(current + 1 < args.get(i).size()) {
                    indexList.set(i, current + 1);
                    break;
                }
                else {
                    indexList.set(i, 0);
                }
                
                if(indexList.get(i) == 0 && i==0) {
                    done = true;
                    break;
                }
            }
        }
    }

}
