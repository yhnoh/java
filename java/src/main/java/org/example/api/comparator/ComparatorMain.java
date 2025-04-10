package org.example.api.comparator;

import java.util.Arrays;
import java.util.Comparator;

public class ComparatorMain {

    public static void main(String[] args) {
        InternetProtocol ip1 = new InternetProtocol("1.1.1.2");
        InternetProtocol ip2 = new InternetProtocol("2.1.1.1");


        //Comparable 인터페이스를 이용한 비교
        int compareTo = ip1.compareTo(ip2);
        System.out.println("===========================Comparable===========================");
        System.out.println("compareTo = " + compareTo);

        if (ip1.compareTo(ip2) > 0) {
            System.out.println(ip1 + "이 " + ip2 + "보다 큽니다.");
        } else if (ip1.compareTo(ip2) < 0) {
            System.out.println(ip1 + "이 " + ip2 + "보다 작습니다.");
        } else if (ip1.compareTo(ip2) == 0) {
            System.out.println(ip1 + "이 " + ip2 + "보다 작습니다.");
        }

        InternetProtocol[] ips = {ip1, ip2};
        Arrays.sort(ips);
        System.out.println("ips = " + Arrays.toString(ips));

        System.out.println();
        System.out.println();

        System.out.println("===========================Comparator===========================");
        Comparator<InternetProtocol> comparator = new Octet4Comparator();
        //Comparator 인터페이스를 이용한 비교
        int compare = comparator.compare(ip1, ip2);
        System.out.println("compare = " + compare);

        if (comparator.compare(ip1, ip2) > 0) {
            System.out.println(ip1 + "이 " + ip2 + "보다 큽니다.");
        } else if (comparator.compare(ip1, ip2) < 0) {
            System.out.println(ip1 + "이 " + ip2 + "보다 작습니다.");
        } else if (comparator.compare(ip1, ip2) == 0) {
            System.out.println(ip1 + "이 " + ip2 + "보다 작습니다.");
        }

        Arrays.sort(ips, comparator);
        System.out.println("ips = " + Arrays.toString(ips));


    }
}
