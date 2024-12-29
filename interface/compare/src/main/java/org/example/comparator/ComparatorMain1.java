package org.example.comparator;

import org.example.InternetProtocol;

import java.util.Arrays;

public class ComparatorMain1 {

    public static void main(String[] args) {
        Octet4Comparator comparator = new Octet4Comparator();
        ComparatorInternetProtocol ip1 = new ComparatorInternetProtocol("1.1.1.3");
        ComparatorInternetProtocol ip2 = new ComparatorInternetProtocol("1.1.2.2");

        int compareTo = ip1.compareTo(ip2);
        System.out.println("compareTo = " + compareTo);

        int compare = comparator.compare(ip1, ip2);
        System.out.println("compare = " + compare);

        InternetProtocol[] ips = {ip1, ip2};

        Arrays.sort(ips);
        System.out.println("ips = " + Arrays.toString(ips));
        Arrays.sort(ips, comparator);
        System.out.println("ips = " + Arrays.toString(ips));
    }
}
