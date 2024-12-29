package org.example;

public abstract class InternetProtocol {

    private final String ip;
    private int octet1;
    private int octet2;
    private int octet3;
    private int octet4;

    public InternetProtocol(String ip) {
        this.ip = ip;
        String[] octets = ip.split("\\.");
        octet1 = Integer.valueOf(octets[0]);
        octet2 = Integer.valueOf(octets[1]);
        octet3 = Integer.valueOf(octets[2]);
        octet4 = Integer.valueOf(octets[3]);
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
}
