package array;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.junit.Test;

public class FindTwoEqualSums {
    
    @Test
    public void testSuccess() {
        
        int[] a = {3,4,7,1,2,9,8};
        
        int[][] returned = getEqualPairs(a);
        
        for (int[] arr : returned) {
            System.out.println(Arrays.toString(arr));
        }
    }
    
    
    public int[][] getEqualPairs(int[] a) {
        
        List<List<Integer>> cords = new LinkedList<List<Integer>>();
        
        Map<Integer, List<Pair>> map = new HashMap<Integer, List<Pair>>();
        
        for(int i = 0; i < a.length; i++) {
            for(int j = i+1; j < a.length; j++) {
                
                int key = a[i] + a[j];
                if(map.containsKey(key)) {
                    List<Pair> pairs = map.get(key);
                    for(Pair pair: pairs) {
                        cords.add(Arrays.asList(pair.a, pair.b, i, j));
                    }
                    pairs.add(new Pair(i,j));
                    map.put(key, pairs);
                }
                else {
                    List<Pair> pairs = new LinkedList<Pair>();
                    pairs.add(new Pair(i,j));
                    map.put(key, pairs);
                }
            }
        }
        
        int[][] toReturn = new int[cords.size()][4];
        
        for(int i = 0; i < cords.size(); i++) {
            List<Integer> toAdd = cords.get(i);
            for(int j = 0; j < toAdd.size(); j++) {
                toReturn[i][j] = toAdd.get(j);
            }
        }
        
        return toReturn;
    }
    
    private class Pair {
        public Pair(int a, int b) {
            this.a = a;
            this.b = b;
        }

        int a, b;
    }
}
