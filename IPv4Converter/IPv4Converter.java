import java.util.LinkedList;

public class IPv4Converter {

    public static String toString(long ipLong) {
        LinkedList<String> octets = new LinkedList<>();
        int octet;
        int mask = 255;
        for (int i = 0; i < 4; i++) {
            octet = (int) (ipLong & mask); // mask -- 1111 1111 to get 1 byte(octet) for each part of ipv4
            octets.add(Integer.toString(octet));
            ipLong >>= 8; // shifting to get next octet
        }

        return String.join(".", octets.reversed()); // formatting string for ipv4
    }

    public static long toLong(String ipStr) {
        long ipLong = 0;
        String[] octets = ipStr.split("\\.");
        int shift = 24;
        for (int i = 0; i < 4; i++) {
            ipLong |= Long.parseLong(octets[i]) << shift; // to get octet in the right place shifting is necessary
            shift -= 8; // decrease shift to order next octet
        }

        return ipLong;
    }
}
