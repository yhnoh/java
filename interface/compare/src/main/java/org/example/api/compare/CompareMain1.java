package org.example.api.compare;

public class CompareMain1 {

    public static void main(String[] args) {



        CompareInternetProtocol ip1 = new CompareInternetProtocol("127.2.0.0");
        CompareInternetProtocol ip2 = new CompareInternetProtocol("127.1.0.0");

//        CompareInternetProtocol ip1 = new CompareInternetProtocol("127.1.0.0");
//        CompareInternetProtocol ip2 = new CompareInternetProtocol("127.2.0.0");

//        CompareInternetProtocol ip1 = new CompareInternetProtocol("127.1.0.0");
//        CompareInternetProtocol ip2 = new CompareInternetProtocol("127.1.0.0");

        //Comparable 인터페이스를 이용한 비교
        if(ip1.compareTo(ip2) > 0) {
            System.out.println(ip1 + "이 " + ip2 + "보다 큽니다.");
        } else if(ip1.compareTo(ip2) < 0) {
            System.out.println(ip1 + "이 " + ip2 + "보다 작습니다.");
        } else if (ip1.compareTo(ip2) == 0) {
            System.out.println(ip1 + "과 " + ip2 + "가 같습니다.");
        }

    }

}
