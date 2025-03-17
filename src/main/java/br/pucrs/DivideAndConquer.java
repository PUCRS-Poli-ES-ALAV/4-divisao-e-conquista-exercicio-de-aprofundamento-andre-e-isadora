package br.pucrs;
import java.util.Random;

public class DivideAndConquer {
    static int iterationsMergeSort = 0;
    static int iterationsMaxVal1 = 0;
    static int iterationsMaxVal2 = 0;
    static int iterationsMultiply = 0;

    public static void mergeSort(long[] arr, int left, int right) {
        if (left < right) {
            int mid = left + (right - left) / 2;
            mergeSort(arr, left, mid);
            mergeSort(arr, mid + 1, right);
            merge(arr, left, mid, right);
        }
    }

    private static void merge(long[] arr, int left, int mid, int right) {
        int n1 = mid - left + 1;
        int n2 = right - mid;
        long[] L = new long[n1];
        long[] R = new long[n2];
        System.arraycopy(arr, left, L, 0, n1);
        System.arraycopy(arr, mid + 1, R, 0, n2);

        int i = 0, j = 0, k = left;
        while (i < n1 && j < n2) {
            iterationsMergeSort++;
            if (L[i] <= R[j]) {
                arr[k++] = L[i++];
            } else {
                arr[k++] = R[j++];
            }
        }
        while (i < n1) arr[k++] = L[i++];
        while (j < n2) arr[k++] = R[j++];
    }

    public static long maxVal1(long[] arr) {
        long max = arr[0];
        for (int i = 1; i < arr.length; i++) {
            iterationsMaxVal1++;
            if (arr[i] > max) max = arr[i];
        }
        return max;
    }

    public static long maxVal2(long[] arr, int left, int right) {
        iterationsMaxVal2++;
        if (left == right) return arr[left];
        int mid = left + (right - left) / 2;
        long max1 = maxVal2(arr, left, mid);
        long max2 = maxVal2(arr, mid + 1, right);
        return Math.max(max1, max2);
    }

    public static long multiply(long x, long y, int n) {
        iterationsMultiply++;
        if (n == 1) return x * y;
        int m = (int) Math.ceil(n / 2.0);
        long a = x >> m, b = x & ((1L << m) - 1);
        long c = y >> m, d = y & ((1L << m) - 1);
        long e = multiply(a, c, m);
        long f = multiply(b, d, m);
        long g = multiply(b, c, m);
        long h = multiply(a, d, m);
        return (e << (2 * m)) + ((g + h) << m) + f;
    }

    public static void main(String[] args) {
        int[] sizes = {32, 2048, 1048576};
        Random rand = new Random();
        for (int size : sizes) {
            long[] arr = rand.longs(size, 0, 10000).toArray();
            long[] arrCopy = arr.clone();

            iterationsMergeSort = 0;
            long start = System.nanoTime();
            mergeSort(arr, 0, arr.length - 1);
            long end = System.nanoTime();
            System.out.println("MergeSort - Size: " + size + " | Iterations: " + iterationsMergeSort + " | Time: " + (end - start) + " ns");

            iterationsMaxVal1 = 0;
            start = System.nanoTime();
            maxVal1(arrCopy);
            end = System.nanoTime();
            System.out.println("MaxVal1 - Size: " + size + " | Iterations: " + iterationsMaxVal1 + " | Time: " + (end - start) + " ns");

            iterationsMaxVal2 = 0;
            start = System.nanoTime();
            maxVal2(arrCopy, 0, arrCopy.length - 1);
            end = System.nanoTime();
            System.out.println("MaxVal2 - Size: " + size + " | Iterations: " + iterationsMaxVal2 + " | Time: " + (end - start) + " ns");
        }

        long x = 15, y = 10;
        iterationsMultiply = 0;
        long start = System.nanoTime();
        multiply(x, y, 4);
        long end = System.nanoTime();
        System.out.println("Multiply (4-bit) - Iterations: " + iterationsMultiply + " | Time: " + (end - start) + " ns");
    }
}
