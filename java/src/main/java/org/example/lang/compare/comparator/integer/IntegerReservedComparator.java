package org.example.lang.compare.comparator.integer;

import java.util.Comparator;

/**
 * 정수형을 내림차순으로 정렬하는 Comparator 클래스
 */
public class IntegerReservedComparator implements Comparator<Integer> {

    // 기본
    @Override
    public int compare(Integer o1, Integer o2) {
        int compare = Integer.compare(o1, o2);
        return compare * -1;
    }
}
