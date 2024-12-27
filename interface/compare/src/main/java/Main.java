import compare.InternetProtocol;

import java.lang.reflect.Array;
import java.util.*;

public class Main {



    public static void main(String[] args) {
        InternetProtocol ip1 = new InternetProtocol("127.0.0.3");
        InternetProtocol ip2 = new InternetProtocol("127.0.0.2");
        InternetProtocol ip3 = new InternetProtocol("127.0.0.1");

        int i = ip1.hashCode();


        InternetProtocol[] ips = {ip1, ip3, ip2};
        System.out.println("ips = " + Arrays.toString(ips));

        //오름차순
        Arrays.sort(ips);
        System.out.println("ips = " + Arrays.toString(ips));

        //내림차순
        Arrays.sort(ips, Collections.reverseOrder());
        System.out.println("ips = " + Arrays.toString(ips));

    }
}
