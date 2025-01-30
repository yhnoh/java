package org.example.api.comparator.integer;

import java.util.Comparator;

public class IntegerReservedComparator implements Comparator<Integer> {

    // 기본
    @Override
    public int compare(Integer o1, Integer o2) {
        int compare = Integer.compare(o1, o2);
        return compare * -1;
    }
}
