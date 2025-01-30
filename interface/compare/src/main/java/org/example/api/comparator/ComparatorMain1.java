package org.example.api.comparator;

import java.util.Comparator;

public class ComparatorMain1 {

    public static void main(String[] args) {
        ComparatorInternetProtocol ip1 = new ComparatorInternetProtocol("1.1.1.2");
        ComparatorInternetProtocol ip2 = new ComparatorInternetProtocol("1.1.2.1");

        Comparator<ComparatorInternetProtocol> comparator = new Octet4Comparator();

        //Comparable 인터페이스를 이용한 비교
        int compareTo = ip1.compareTo(ip2);
        System.out.println("compareTo = " + compareTo);

        //Comparator 인터페이스를 이용한 비교
        int compare = comparator.compare(ip1, ip2);
        System.out.println("compare = " + compare);

    }
}
