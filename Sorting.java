import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.List;
import java.util.LinkedList;

/**
 * Implementation of an ArrayQueue.
 *
 * @author Abhinav Kaushik
 * @version 1.0
 */
public class Sorting {

    /**
     * Implement selection sort.
     *
     * It should be:
     * in-place
     * unstable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n^2)
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void selectionSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("Cannot have null array.");
        }
        for (int i = 0; i < arr.length; i++) {
            int minIdx = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (comparator.compare(arr[j], arr[minIdx]) < 0) {
                    minIdx = j;
                }
            }
            T temp = arr[i];
            arr[i] = arr[minIdx];
            arr[minIdx] = temp;
        }
    }

    /**
     * Implement cocktail sort.
     *
     * It should be:
     * in-place
     * stable
     * adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n)
     *
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void cocktailSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("Cannot have null array.");
        }
        boolean swapped = true;
        int startIdx = 0;
        int endIdx = arr.length - 1;
        while (swapped) {
            swapped = false;
            int index = endIdx;
            for (int i = startIdx; i < index; i++) {
                if (comparator.compare(arr[i], arr[i + 1]) > 0) {
                    T temp = arr[i];
                    arr[i] = arr[i + 1];
                    arr[i + 1] = temp;
                    swapped = true;
                    endIdx = i;
                }
            }
            if (swapped) {
                swapped = false;
                index = startIdx;
                for (int i = endIdx; i > index; i--) {
                    if (comparator.compare(arr[i - 1], arr[i]) > 0) {
                        T temp = arr[i];
                        arr[i] = arr[i - 1];
                        arr[i - 1] = temp;
                        swapped = true;
                        startIdx = i;
                    }
                }
            }
        }
    }

    /**
     * Implement merge sort.
     *
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n log n)
     *
     * And a best case running time of:
     * O(n log n)
     *
     * You can create more arrays to run merge sort, but at the end, everything
     * should be merged back into the original T[] which was passed in.
     *
     * When splitting the array, if there is an odd number of elements, put the
     * extra data on the right side.
     *
     * Hint: If two data are equal when merging, think about which subarray
     * you should pull from first
     *
     * @param <T>        data type to sort
     * @param arr        the array to be sorted
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void mergeSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("Cannot have null array.");
        }
        if (arr.length == 0) {
            return;
        }
        if (arr.length == 1) {
            return;
        }
        int length = arr.length;
        int midIdx = length / 2;
        T[] left = (T[]) new Object[midIdx];
        T[] right = (T[]) new Object[length - midIdx];
        for (int i = 0; i < midIdx; i++) {
            left[i] = arr[i];
            right[i] = arr[i + midIdx];
        }
        right[right.length - 1] = arr[length - 1];
        mergeSort(left, comparator);
        mergeSort(right, comparator);
        int a = 0;
        int b = 0;
        while ((a <= left.length - 1) && (b <= right.length - 1)) {
            if (comparator.compare(left[a], right[b]) <= 0) {
                arr[a + b]  = left[a];
                a++;
            } else {
                arr[a + b]  = right[b];
                b++;
            }
        }
        while (a < left.length) {
            arr[a + b] = left[a];
            a++;
        }
        while (b < right.length) {
            arr[a + b] = right[b];
            b++;
        }
    }


    /**
     * Implement quick sort.
     *
     * Use the provided random object to select your pivots. For example if you
     * need a pivot between a (inclusive) and b (exclusive) where b > a, use
     * the following code:
     *
     * int pivotIndex = rand.nextInt(b - a) + a;
     *
     * If your recursion uses an inclusive b instead of an exclusive one,
     * the formula changes by adding 1 to the nextInt() call:
     *
     * int pivotIndex = rand.nextInt(b - a + 1) + a;
     *
     * It should be:
     * in-place
     * unstable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n log n)
     *
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @param random       the Random object used to select pivots
     * @throws java.lang.IllegalArgumentException if the array or comparator or
     *                                            rand is null
     */
    public static <T> void quickSort(T[] arr, Comparator<T> comparator,
                                     Random random) {
        if (arr == null || comparator == null || random == null) {
            throw new IllegalArgumentException("Cannot sort with null array.");
        }
        quickSortHelper(arr, 0, arr.length - 1, comparator, random);

    }

    /**
     * Helper for quickSort algorithm.
     * @param arr sorted array
     * @param start starting index
     * @param end ending index
     * @param comparator Comparator
     * @param rand random Object
     * @param <T> data type being sorted
     */
    private static <T> void quickSortHelper(T[] arr, int start, int end, Comparator<T> comparator, Random rand) {
        if (end - start < 1) {
            return;
        }
        int pivotIdx = rand.nextInt(end - start + 1) + start;
        T pivot = arr[pivotIdx];
        arr[pivotIdx] = arr[start];
        arr[start] = pivot;
        int i = start + 1;
        int j = end;
        while (i <= j) {
            while (i <= j && comparator.compare(arr[i], pivot) <= 0) {
                i++;
            }
            while (i <= j && comparator.compare(arr[j], pivot) >= 0) {
                j--;
            }
            if (i <= j) {
                T temp = arr[j];
                arr[j] = arr[i];
                arr[i] = temp;
                i++;
                j--;
            }
        }
        T temp = arr[j];
        arr[j] = arr[start];
        arr[start] = temp;
        quickSortHelper(arr, start, j - 1, comparator, rand);
        quickSortHelper(arr, j + 1, end, comparator, rand);
    }

    /**
     * Implement LSD (least significant digit) radix sort.
     *
     *
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(kn)
     *
     * And a best case running time of:
     * O(kn)
     *
     * You are allowed to make an initial O(n) passthrough of the array to
     * determine the number of iterations you need. The number of iterations
     * can be determined using the number with the largest magnitude.
     *
     * At no point should you find yourself needing a way to exponentiate a
     * number; any such method would be non-O(1). Think about how how you can
     * get each power of BASE naturally and efficiently as the algorithm
     * progresses through each digit.
     *
     *
     * You may use ArrayList or LinkedList if you wish, but it may only be
     * used inside radix sort and any radix sort helpers. Do NOT use these
     * classes with other sorts. However, be sure the List implementation you
     * choose allows for stability while being as efficient as possible.
     *
     * Do NOT use anything from the Math class except Math.abs().
     *
     * @param arr the array to be sorted
     * @throws java.lang.IllegalArgumentException if the array is null
     */
    public static void lsdRadixSort(int[] arr) {
        if (arr == null) {
            throw new IllegalArgumentException("Cannot have null array.");
        }
        if (arr.length == 0) {
            return;
        }
        LinkedList<Integer>[] bucket = new LinkedList[20];
        for (int i = 0; i < 20; i++) {
            bucket[i] = new LinkedList<>();
        }
        int max = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] > max) {
                max = arr[i];
            }
        }
        int k = 0;
        while (max != 0) {
            max = max / 10;
            k++;
        }
        int value = 1;
        for (int i = 1; i <= k; i++) {
            for (int j = 0; j < arr.length; j++) {
                int digit = (arr[j] / value) % 10;
                bucket[digit + 9].add(arr[j]);
            }
            value = value * 10;
            int idx = 0;
            for (LinkedList<Integer> list : bucket) {
                while (!list.isEmpty()) {
                    arr[idx++] = list.removeFirst();
                }
            }
        }
    }

    /**
     * Implement heap sort.
     *
     * It should be:
     * out-of-place
     * unstable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n log n)
     *
     * And a best case running time of:
     * O(n log n)
     *
     * Use java.util.PriorityQueue as the heap. Note that in this
     * PriorityQueue implementation, elements are removed from smallest
     * element to largest element.
     *
     * Initialize the PriorityQueue using its build heap constructor (look at
     * the different constructors of java.util.PriorityQueue).
     *
     * Return an int array with a capacity equal to the size of the list. The
     * returned array should have the elements in the list in sorted order.
     *
     * @param data the data to sort
     * @return the array with length equal to the size of the input list that
     * holds the elements from the list in sorted order
     * @throws java.lang.IllegalArgumentException if the data is null
     */
    public static int[] heapSort(List<Integer> data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot have null data.");
        }
        PriorityQueue<Integer> prioQ = new PriorityQueue<>(data);
        int[] arr = new int[data.size()];
        int i = 0;
        while (!prioQ.isEmpty()) {
            arr[i] = prioQ.poll();
            i++;
        }
        return arr;
    }
}