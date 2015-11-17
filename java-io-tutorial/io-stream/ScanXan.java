import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Scanner;

/**
 * Breaking Input into Tokens.
 */
public class ScanXan {
    public static void main(String[] args) throws IOException {

        // Scanner are userful for breaking down formatted input into tokens and translating individual tokens according to their data type
        Scanner s = null;

        try {
            s = new Scanner(new BufferedReader(new FileReader("ScanXan.java")));

            while (s.hasNext()) {
                System.out.println(s.next());
            }
        } finally {
            if (s != null) {
                s.close();
            }
        }
    }
}

