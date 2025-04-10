package org.example.api.comparator;

public class InternetProtocol implements Comparable<InternetProtocol> {

    private final String ip;
    private final int octet1;
    private final int octet2;
    private final int octet3;
    private final int octet4;

    public InternetProtocol(String ip) {
        this.ip = ip;
        String[] octets = ip.split("\\.");
        octet1 = Integer.parseInt(octets[0]);
        octet2 = Integer.parseInt(octets[1]);
        octet3 = Integer.parseInt(octets[2]);
        octet4 = Integer.parseInt(octets[3]);
    }

    @Override
    public String toString() {
        return ip;
    }

    public int getOctet1() {
        return octet1;
    }

    public int getOctet2() {
        return octet2;
    }

    public int getOctet3() {
        return octet3;
    }

    public int getOctet4() {
        return octet4;
    }

    @Override
    public int compareTo(InternetProtocol o) {
        if (this.getOctet1() > o.getOctet1()) {
            return 1;
        } else if (this.getOctet1() < o.getOctet1()) {
            return -1;
        }

        if (this.getOctet2() > o.getOctet2()) {
            return 1;
        } else if (this.getOctet2() < o.getOctet2()) {
            return -1;
        }

        if (this.getOctet3() > o.getOctet3()) {
            return 1;
        } else if (this.getOctet3() < o.getOctet3()) {
            return -1;
        }

        if (this.getOctet4() > o.getOctet4()) {
            return 1;
        } else if (this.getOctet4() < o.getOctet4()) {
            return -1;
        }

        return 0;
    }
}
