import java.util.Scanner;

public class Main {

    // to speed up computation everything is completed in integer format
    public static String solveProblem(long n) {
        long iVal = 10_000_000; // there is can be 1_000_000, but for more precise division value is multiplied by 10
        long midVal = 100; // just starting value to enter while loop (midVal > 10)
        long x = n;

        // loop that will calculate initial value in int format
        while (midVal > 10 && n > 1) {
            midVal = 10_000_000 / x;
            iVal += midVal;
            n--;
            x *= n;
        }

        // round up to 6 digits
        if (iVal % 10 >= 5) {
            iVal /= 10;
            iVal++;
        } else {
            iVal /= 10;
        }

        StringBuilder resultStr = new StringBuilder(Long.toString(iVal));
        System.out.println(resultStr.length());
        // add floating point
        resultStr.insert(1, ".");
        return resultStr.toString();
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        long n = scanner.nextLong();

        System.out.println(solveProblem(n));
    }
}
