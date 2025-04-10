package org.example.api.compare;

import java.util.Arrays;
import java.util.Collections;

public class CompareMain {

    public static void main(String[] args) {


        InternetProtocol ip1 = new InternetProtocol("127.0.0.3");
        InternetProtocol ip2 = new InternetProtocol("127.0.0.2");
        InternetProtocol ip3 = new InternetProtocol("127.0.0.1");

//        InternetProtocol ip1 = new InternetProtocol("127.0.3.0");
//        InternetProtocol ip2 = new InternetProtocol("127.0.2.0");
//        InternetProtocol ip3 = new InternetProtocol("127.0.1.0");

//        InternetProtocol ip1 = new InternetProtocol("127.3.0.0");
//        InternetProtocol ip2 = new InternetProtocol("127.2.0.0");
//        InternetProtocol ip3 = new InternetProtocol("127.1.0.0");


        //Comparable 인터페이스를 이용한 비교
        if (ip1.compareTo(ip2) > 0) {
            System.out.println(ip1 + "이 " + ip2 + "보다 큽니다.");
        } else if (ip1.compareTo(ip2) < 0) {
            System.out.println(ip1 + "이 " + ip2 + "보다 작습니다.");
        } else if (ip1.compareTo(ip2) == 0) {
            System.out.println(ip1 + "과 " + ip2 + "가 같습니다.");
        }

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
