package org.example.io.iostream;

import java.io.*;

public class DataStreamMain1 {

    public static void main(String[] args) throws IOException {

        try (DataOutputStream os = new DataOutputStream(new BufferedOutputStream(new FileOutputStream("./tmp/data.txt")))) {
            double[] prices = {19.99, 9.99, 15.99, 3.99, 4.99};
            int[] units = {12, 8, 13, 29, 50};
            String[] descs = {
                    "Java T-shirt",
                    "Java Mug",
                    "Duke Juggling Dolls",
                    "Java Pin",
                    "Java Key Chain"
            };

            for (int i = 0; i < prices.length; i++) {
                os.writeDouble(prices[i]);
                os.writeInt(units[i]);
                os.writeUTF(descs[i]);
            }

        }

        try (DataInputStream is = new DataInputStream(new BufferedInputStream(new FileInputStream("./tmp/data.txt")))) {
            try {
                while (true) {

                    double price = is.readDouble();
                    int unit = is.readInt();
                    String desc = is.readUTF();

                    System.out.printf("price = %f, unit = %d, desc = %s%n", price, unit, desc);
                }
            } catch (EOFException e) {
                System.out.println("End of file");
            }
        }
    }
}
