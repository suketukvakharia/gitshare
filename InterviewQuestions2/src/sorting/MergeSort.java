package sorting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class MergeSort {
    
    
    
    public static void main(String[] args) {
        // 10 elements
        List<Integer> list = Arrays.asList(4,5,2,5,233,21,34,999,331,43);
        
        list = measureSortAndVerify(list);
        
        // 100 elements.
        generateAndSort(100);
        
        // 1000 elements.
        generateAndSort(1000);
        
        // 10000 elements.
        generateAndSort(10000);
        
        // 100000 elements.
        generateAndSort(100000);
        
        // 1000000 elements.
        generateAndSort(1000000);
        
        // 10000000 elements.
        generateAndSort(10000000);
    }



    private static void generateAndSort(int listSize) {
        List<Integer> toSort = new ArrayList<>();
        Random rand = new Random();
        for(int i = 0; i < listSize; i++) {
            toSort.add(rand.nextInt());
        }
        measureSortAndVerify(toSort);
    }



    private static List<Integer> measureSortAndVerify(List<Integer> list) {
        long start = System.nanoTime();
        list = sort(list);
        
        long milliseconds = TimeUnit.MILLISECONDS.convert( System.nanoTime() - start, TimeUnit.NANOSECONDS);
        
        System.out.println("Took this long:" + milliseconds + " to sort these many items" + list.size());
        
        int previous = list.get(0)-1;
        for (Integer current : list) {
            if(current < previous) {
                throw new IllegalStateException("Sorting is fucked up");
            }
        }
        System.out.println("Sorting is done right.");
//        System.out.println("sorted" + list);
        return list;
    }
    
    
    
    public static <T extends Comparable<T>> List<T> sort(List<T> toSort) {
        if(toSort.size() <= 1) return toSort;
        
        List<T> left = sort(toSort.subList(0, toSort.size()/2));
        
        List<T> right = sort(toSort.subList(toSort.size()/2, toSort.size()));
        
        // merge the left and right in toSort.
        List<T> newList = new ArrayList<>();
        for(int leftIndex = 0, rightIndex = 0; leftIndex < left.size() || rightIndex < right.size(); ) {
            
            if(leftIndex < left.size() &&  ( rightIndex >= right.size() || left.get(leftIndex).compareTo(right.get(rightIndex)) <= 0)) {
                newList.add(left.get(leftIndex++));
            } else {
                newList.add(right.get(rightIndex++));
            }
        }
        return newList;
    }

}
