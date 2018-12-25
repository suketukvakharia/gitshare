package sorting;

import java.util.Arrays;
import java.util.Random;

public class QuickSort {
    
    
    public static void main(String[] args) {
        
        int size = 100;
        int[] arr = new int[size];
        
        Random rand = new Random();
        for(int i = 0; i < size; i++) 
            arr[i] = rand.nextInt(10*size);
        
        quickSort(arr);
        
        int[] copyOf = Arrays.copyOf(arr, arr.length);
        Arrays.sort(copyOf);
        
        // check equal
        for(int i = 0; i < size; i++) 
            if(arr[i] != copyOf[i]) {
                throw new IllegalStateException("Not equal for i:" + i + " arr val:" + arr[i] + " copyOfVal:" + copyOf[i]);
            }
        
        System.out.println("good run");
    }

    private static void quickSort(int[] arr) {
        if(arr == null || arr.length == 0) return;
        
        quicksort(arr, 0, arr.length-1);
    }

    private static void quicksort(int[] arr, int beg, int end) {
        if(beg >= end) return;
        
        int pivotIndex = beg + (new Random()).nextInt(end-beg);
        int pivot = arr[pivotIndex];
        
        swap(arr, pivotIndex, beg);
        pivotIndex = beg;
        
        int i = beg + 1, j = end;
        
        while( i < j) {
            
            while(i < j && arr[i] <= pivot) {
                i++;
            }
            
            while(j > i && arr[j] > pivot) {
                j--;
            }
            
            if(i < j) {
                swap(arr, i, j);
            }
        }
        
        if(arr[i] < pivot) {
            swap(arr, i, pivotIndex);
        } else {
            i--;
            swap(arr, i, pivotIndex);
        }
        quicksort(arr, beg, i-1);
        quicksort(arr, i+1, end);
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

}
