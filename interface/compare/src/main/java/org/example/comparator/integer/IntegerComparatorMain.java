package org.example.comparator.integer;

import java.util.Arrays;

public class IntegerComparatorMain {
    public static void main(String[] args) {
        Integer[] integers1 = {5, 4, 3, 2, 1};
        Arrays.sort(integers1);
        //[1, 2, 3, 4, 5]
        System.out.println(Arrays.toString(integers1));

        IntegerReservedComparator integerReservedComparator = new IntegerReservedComparator();
        Integer[] integers2 = {1, 2, 3, 4, 5};
        Arrays.sort(integers2, integerReservedComparator);
        //[5, 4, 3, 2, 1]
        System.out.println(Arrays.toString(integers2));
    }
}
