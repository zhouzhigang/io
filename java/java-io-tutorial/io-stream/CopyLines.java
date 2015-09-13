import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

/**
 * Line-Oriented IO.
 * Use FileReader and FileWriter to copy character file.
 */
public class CopyLines {
    public static void copy(String srcPath, String destPath) throws IOException {

        BufferedReader in = null;
        BufferedWriter out = null;

        try {
            in = new BufferedReader(new FileReader(srcPath));
            out = new BufferedWriter(new FileWriter(destPath));

            String l;
            while ((l = in.readLine()) != null) {
                out.write(l);
                // each line should end with "\r\n"
                out.write("\r\n");
            }
        } finally {
            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.close();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        String srcPath = "CopyLines.java";
        String destPath = "CopyLines.bak";
        if (args.length < 2) {
            System.out.format("==== Usage ====%n    java CopyLines 'srcPath' 'destpath'%n");
            System.out.format("==== Example ====%n    java CopyLines %s %s%n", srcPath, destPath);
        } else {
            srcPath = args[0];
            destPath = args[1];
        }
        CopyLines.copy(srcPath, destPath);
        System.out.format("Copy successful%n");
    }
}
