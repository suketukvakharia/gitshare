package sorting;

import java.util.Arrays;
import java.util.Random;

public class QuickSort2 {

    public static void main(String[] args) {
        int numRuns = 100;
        Random rand = new Random();
        for (int j = 0; j < numRuns; j++) {

            int size = rand.nextInt(500);
            int[] arr = new int[size];

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
    }

    public static void quickSort(int[] arr) {
        // recursive
        quickSort(arr, 0, arr.length-1);
    }

    private static void quickSort(int[] arr, int start, int end) {
        if (start >= end) {
            return;
        }

        // pick pivot
        // normally we could survey three values and pick the middle, pick the end as pivot for brevity
        int pivotIndex = end;
        int pivotVal = arr[pivotIndex];

        int lowerThanPivotIndex = start;
        for (int i = start; i < end; i++) {
            if (arr[i] < pivotVal) {
                int temp = arr[i];
                arr[i] = arr[lowerThanPivotIndex];
                arr[lowerThanPivotIndex++] = temp;
            }
        }
        arr[end] = arr[lowerThanPivotIndex];
        arr[lowerThanPivotIndex] = pivotVal;

        // we know that pivot is in right index now, recurse not counting pivot
        quickSort(arr, start, lowerThanPivotIndex-1);
        quickSort(arr, lowerThanPivotIndex + 1, end);
    }
}
