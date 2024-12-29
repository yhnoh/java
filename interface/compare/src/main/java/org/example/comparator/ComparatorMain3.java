package org.example.comparator;

import org.example.InternetProtocol;

import java.util.Arrays;
import java.util.Comparator;

public class ComparatorMain3 {

    public static void main(String[] args) {

        ComparatorInternetProtocol ip1 = new ComparatorInternetProtocol("1.1.1.2");
        ComparatorInternetProtocol ip2 = new ComparatorInternetProtocol("1.1.2.1");

        ComparatorInternetProtocol[] ips = {ip1, ip2};

        //Comparable 인터페이스를 이용한 기본 정렬
        Arrays.sort(ips);
        System.out.println("ips = " + Arrays.toString(ips));

        //Comparator 인터페이스를 이용한 정렬
        Comparator<ComparatorInternetProtocol> comparator = new Octet4Comparator();
        Arrays.sort(ips, comparator);
        System.out.println("ips = " + Arrays.toString(ips));

    }
}
