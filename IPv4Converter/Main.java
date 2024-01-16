import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Input ipv4 in string format:\n");
        String ipStr = scanner.nextLine();
        System.out.println("Input ipv4 in int32 format:\n");
        long ipLong = scanner.nextLong();
        System.out.println(IPv4Converter.toLong(ipStr));
        System.out.println(IPv4Converter.toString(ipLong));
    }
}
