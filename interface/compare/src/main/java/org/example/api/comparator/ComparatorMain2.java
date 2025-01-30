package org.example.api.comparator;

import java.util.Comparator;

public class ComparatorMain2 {

    public static void main(String[] args) {
        ComparatorInternetProtocol ip1 = new ComparatorInternetProtocol("1.1.1.2");
        ComparatorInternetProtocol ip2 = new ComparatorInternetProtocol("1.1.2.1");

//        CompareInternetProtocol ip1 = new CompareInternetProtocol("1.1.1.1");
//        CompareInternetProtocol ip2 = new CompareInternetProtocol("1.1.2.2");
//
//        CompareInternetProtocol ip1 = new CompareInternetProtocol("1.1.1.1");
//        CompareInternetProtocol ip2 = new CompareInternetProtocol("1.1.1.1");
        //Comparable 인터페이스를 이용한 비교
        Comparator<ComparatorInternetProtocol> comparator = new Octet4Comparator();

        if(comparator.compare(ip1, ip2) > 0) {
            System.out.println(ip1 + "이 " + ip2 + "보다 큽니다.");
        } else if(comparator.compare(ip1, ip2) < 0) {
            System.out.println(ip1 + "이 " + ip2 + "보다 작습니다.");
        } else if (comparator.compare(ip1, ip2) == 0) {
            System.out.println(ip1 + "과 " + ip2 + "보다 작습니다.");
        }


//        InternetProtocol[] ips = {ip1, ip2};
//
//        Arrays.sort(ips);
//        System.out.println("ips = " + Arrays.toString(ips));
//        Arrays.sort(ips, comparator);
//        System.out.println("ips = " + Arrays.toString(ips));
    }
}
