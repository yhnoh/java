package sort;

import java.util.Arrays;

public class SortMain {

    public static void main(String[] args) {
        Sort sort = new SelectionSort();



        int[] array = {3, 4, -5, 2, 1};
        sort.sort(array);
        System.out.println(Arrays.toString(array));
    }
}
