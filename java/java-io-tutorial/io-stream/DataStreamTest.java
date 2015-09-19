import java.io.DataOutputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.DataInputStream;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Data stream support binary I/O of primitive data type values as well as String values.
 * All data streams implement either the DataInput or DataOutput interface.
 */
public class DataStreamTest {

    // file name
    static final String dataFile = "invoicedata";

    // primitive data that will write to file
    // using floating numbers to represent monetary values is very bad, but data stream doesn't support BigDeciaml(object stream support)
    static final double[] prices = { 19.99, 9.99, 15.99, 3.99, 4.99 };
    static final int[] units = { 12, 8, 14, 29, 50 };
    static final String[] descs = {
        "Java T-shirt",
        "Java mug",
        "Duke Juggling Dolls",
        "Java Pin",
        "Java Key Chain"
    };
    
    /**
     * Use DataOutputStream write primitive data type values to file.
     */
    public static void testDataOutputStream() throws IOException {
        DataOutputStream out = null;
        try {
            out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(dataFile)));

            for (int i = 0; i < prices.length; i++) {
                out.writeDouble(prices[i]);
                out.writeInt(units[i]);
                out.writeUTF(descs[i]);
            }
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

    public static void testDataInputStream()  throws IOException {
        DataInputStream in = null;
        try {
            in = new DataInputStream(new BufferedInputStream(new FileInputStream(dataFile)));
            double price;
            int unit;
            String desc;
            double total = 0.0;

            // read each record in the stream
            while(true) {
                price = in.readDouble();
                unit = in.readInt();
                desc = in.readUTF();
                System.out.format("You ordered %d units of %s at $%.2f%n", unit, desc, price);
                total += unit * price;
            }
        } finally {
            if (in != null) {
                in.close();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        testDataOutputStream();
        testDataInputStream();
    }
}
