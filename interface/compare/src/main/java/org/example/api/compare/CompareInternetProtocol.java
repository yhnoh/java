package org.example.api.compare;

public class CompareInternetProtocol extends InternetProtocol implements Comparable<CompareInternetProtocol> {

    public CompareInternetProtocol(String ip) {
        super(ip);
    }


    @Override
    public int compareTo(CompareInternetProtocol o) {
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
