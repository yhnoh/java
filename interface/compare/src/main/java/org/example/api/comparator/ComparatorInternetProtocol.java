package org.example.api.comparator;

import org.example.api.compare.InternetProtocol;

public class ComparatorInternetProtocol extends InternetProtocol implements Comparable<ComparatorInternetProtocol> {

    public ComparatorInternetProtocol(String ip) {
        super(ip);
    }


    @Override
    public int compareTo(ComparatorInternetProtocol o) {
        if(this.getOctet1() > o.getOctet1()) {
            return 1;
        } else if(this.getOctet1() < o.getOctet1()) {
            return -1;
        }

        if(this.getOctet2() > o.getOctet2()) {
            return 1;
        } else if(this.getOctet2() < o.getOctet2()) {
            return -1;
        }

        if(this.getOctet3() > o.getOctet3()) {
            return 1;
        } else if(this.getOctet3() < o.getOctet3()) {
            return -1;
        }

        if(this.getOctet4() > o.getOctet4()) {
            return 1;
        } else if(this.getOctet4() < o.getOctet4()) {
            return -1;
        }

        return 0;
    }

}
