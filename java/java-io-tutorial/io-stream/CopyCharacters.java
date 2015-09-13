import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Character Stream example.
 * Input and output donw with stream classes automatically translates to and from the local character set.
 * Use FileReader and FileWriter to copy character file.
 */
public class CopyCharacters {
    public static void copy(String srcPath, String destPath) throws IOException {

        FileReader in = null;
        FileWriter out = null;

        try {
            in = new FileReader(srcPath);
            out = new FileWriter(destPath);
            int c;
            // the int variable holds a character value in its last 16 bits when use FileReader
            while ((c = in.read()) != -1) {
                out.write(c);
            }
        } finally {
            // always close streams
            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.close();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        String srcPath = "CopyCharacters.java";
        String destPath = "CopyCharacters.bak";
        if (args.length < 2) {
            System.out.format("==== Usage ====%n    java CopyCharacters 'srcPath' 'destpath'%n");
            System.out.format("==== Example ====%n    java CopyCharacters %s %s%n", srcPath, destPath);
        } else {
            srcPath = args[0];
            destPath = args[1];
        }
        CopyCharacters.copy(srcPath, destPath);
        System.out.format("Copy successful%n");
    }
}
