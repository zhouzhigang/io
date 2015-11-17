/**
 * Formatting.
 * Stream objects that implement formatting are instance of either PrintWriter(character stream), or PrintStream(byte stream).
 * PrintStream: System.out, System.error
 * When we need to create a formatted output stream, use PrintWriter, not PrintStream.
 */
public class Format {

    public static void testPrint() {
        int i = 2;
        double r = Math.sqrt(i);

        System.out.print("The square root of ");
        System.out.print(i);
        System.out.print(" is ");
        System.out.print(r);
        System.out.print(".");

        i = 5;
        r = Math.sqrt(i);
        System.out.println("The square root of " + i + " is " + r + ".");
    }


    public static void testFormat() {
        int i = 2;
        double r = Math.sqrt(i);

        System.out.format("The square root of %d is %f.%n", i, r);

        // Format Specifier
        System.out.format("%f, %1$+020.10f %n", Math.PI);
    }

    public static void main(String[] args) {
        testPrint();
        testFormat();
    }
}
