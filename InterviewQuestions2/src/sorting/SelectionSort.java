package sorting;

public class SelectionSort {
    public static <T extends Comparable<T>> void sort(T[] arr) {
        for (int i = 0; i < arr.length; i++) {
            T currentMin = arr[i];
            int minIndex = i;
            for (int j = i; j < arr.length; j++) {

                if (currentMin.compareTo(arr[j]) > 0) {
                    currentMin = arr[j];
                    minIndex = j;
                }
            }

            // swap minIndex & I
            T temp = arr[i];
            arr[i] = currentMin;
            arr[minIndex] = temp;
        }
    }

    // TODO skipping testing for now.
}
