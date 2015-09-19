import java.io.Console;
import java.util.Arrays;
import java.io.IOException;

/**
 * I/O from the Command Line.
 * Standard Input: System.in
 * Standard Output: System.out
 * Standard Error: System.err
 *
 * For historical reasons, standard streams are byte streams.
 * System.out and System.error are defined as PrintStream object.
 * PrintStream utilizes an internal character stream object to emulate many if the features of character streams.
 * By contrast, System.in is a byte steam with no character stream features.
 * To use System.in as a character stream , wrap it in InputStreamReader.
 */
public class Password {

    public static void main(String[] args) {
        
        // Console is a more advanced alternative to the Standarad Streams
        Console c = System.console();
        if (c == null) {
            System.err.println("No console.");
            System.exit(1);
        }

        String login = c.readLine("Enter your login:");
        char[] oldPassword = c.readPassword("Enter you old password: ");

        if (verify(login, oldPassword)) {
            boolean noMatch;
            do {
                char[] newPassword1 = c.readPassword("Enter your new password: ");
                char[] newPassword2 = c.readPassword("Enter your password again: ");
                noMatch = ! Arrays.equals(newPassword1, newPassword2);
                if (noMatch) {
                    c.format("Passwords don't mathc. Try again.%n");
                } else {
                    change(login, newPassword1);
                    c.format("Password for %s changed.%n", login);
                }
                Arrays.fill(newPassword1, ' ');
                Arrays.fill(newPassword2, ' ');
            } while (noMatch);
        }

        Arrays.fill(oldPassword, ' ');
    }

    // Dummy verify method.
    static boolean verify(String login, char[] password) {
        return true;
    }

    // Dummy change method.
    static void change(String login, char[] password) {
    }
}

