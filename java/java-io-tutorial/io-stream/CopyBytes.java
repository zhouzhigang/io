import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Byte Stream examle.
 * Using FileInputStream and FileOutputStream to copy any kinds of file.
 */
public class CopyBytes {
    public static void copy(String srcPath, String destPath) throws IOException {

        FileInputStream in = null;
        FileOutputStream out = null;

        try {
            in = new FileInputStream(srcPath);
            out = new FileOutputStream(destPath);
            int c;

            // the int variable holds a byte value in its last 8 bits when use FileInputStream
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
        String srcPath = "CopyBytes.class";
        String destPath = "CopyBytes.bak.class";
        if (args.length < 2) {
            System.out.format("==== Usage ====%n    java CopyBytes 'srcPath' 'destpath'%n");
            System.out.format("==== Example ====%n    java CopyBytes %s %s%n", srcPath, destPath);
        } else {
            srcPath = args[0];
            destPath = args[1];
        }
        CopyBytes.copy(srcPath, destPath);
        System.out.format("Copy successful%n");
    }
}
