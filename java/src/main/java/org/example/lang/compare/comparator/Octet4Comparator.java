package org.example.lang.compare.comparator;

import java.util.Comparator;

public class Octet4Comparator implements Comparator<InternetProtocol> {

    @Override
    public int compare(InternetProtocol o1, InternetProtocol o2) {

        if (o1.getOctet4() > o2.getOctet4()) {
            return 1;
        } else if (o1.getOctet4() < o2.getOctet4()) {
            return -1;
        }
        return 0;
    }
}
