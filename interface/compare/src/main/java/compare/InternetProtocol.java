package compare;

import java.util.Locale;
import java.util.Objects;

public class InternetProtocol implements Comparable<InternetProtocol> {


    private String ip;
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
    public int compareTo(InternetProtocol o) {
        if(this.octet1 > o.octet1) {
            return 1;
        } else if(this.octet1 < o.octet1) {
            return -1;
        }

        if(this.octet2 > o.octet2) {
            return 1;
        } else if(this.octet2 < o.octet2) {
            return -1;
        }

        if(this.octet3 > o.octet3) {
            return 1;
        } else if(this.octet3 < o.octet3) {
            return -1;
        }

        if(this.octet4 > o.octet4) {
            return 1;
        } else if(this.octet4 < o.octet4) {
            return -1;
        }

        return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        InternetProtocol that = (InternetProtocol) o;
        return Objects.equals(ip, that.ip);
    }

    @Override
    public int hashCode() {
        return ip.hashCode();
    }

    @Override
    public String toString() {
        return ip;
    }
}
