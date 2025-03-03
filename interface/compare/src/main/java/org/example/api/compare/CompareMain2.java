package org.example.api.compare;

import java.util.*;

public class CompareMain2 {



    public static void main(String[] args) {
        CompareInternetProtocol ip1 = new CompareInternetProtocol("127.0.0.3");
        CompareInternetProtocol ip2 = new CompareInternetProtocol("127.0.0.2");
        CompareInternetProtocol ip3 = new CompareInternetProtocol("127.0.0.1");

        InternetProtocol[] ips = {ip1, ip3, ip2};
        System.out.println("ips = " + Arrays.toString(ips));

        //Comparable 인터페이스를 이용한 오름차순
        Arrays.sort(ips);
        System.out.println("ips = " + Arrays.toString(ips));

        //Comparable 인터페이스를 이용한 내림차순
        Arrays.sort(ips, Collections.reverseOrder());
        System.out.println("ips = " + Arrays.toString(ips));

    }
}
