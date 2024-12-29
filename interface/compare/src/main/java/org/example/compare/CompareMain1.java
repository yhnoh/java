package org.example.compare;

public class CompareMain1 {

    public static void main(String[] args) {



        CompareInternetProtocol ip1 = new CompareInternetProtocol("127.2.0.0");
        CompareInternetProtocol ip2 = new CompareInternetProtocol("127.1.0.0");

//        CompareInternetProtocol ip1 = new CompareInternetProtocol("127.1.0.0");
//        CompareInternetProtocol ip2 = new CompareInternetProtocol("127.2.0.0");

//        CompareInternetProtocol ip1 = new CompareInternetProtocol("127.1.0.0");
//        CompareInternetProtocol ip2 = new CompareInternetProtocol("127.1.0.0");

        if(ip1.compareTo(ip2) > 0) {
            System.out.println(ip1 + "이 " + ip2 + "보다 큽니다.");
        } else if(ip1.compareTo(ip2) < 0) {
            System.out.println(ip1 + "이 " + ip2 + "보다 작습니다.");
        } else {
            System.out.println(ip1 + "과 " + ip2 + "보다 작습니다.");
        }

    }

}
