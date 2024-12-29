package org.example.comparator;

import org.example.InternetProtocol;

import java.util.Comparator;

public class Octet4Comparator implements Comparator<ComparatorInternetProtocol> {

    @Override
    public int compare(ComparatorInternetProtocol o1, ComparatorInternetProtocol o2) {

        if(o1.getOctet4() > o2.getOctet4()) {
            return 1;
        }else if(o1.getOctet4() < o2.getOctet4()) {
            return -1;
        }
        return 0;
    }
}
