package org.example.io.iostream;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class ByteStreamMain2 {

    public static void main(String[] args) throws IOException {

        Charset charset = StandardCharsets.UTF_8;
        String str = "Hello World!";
        System.out.println("Hello World! bytes length = " + str.getBytes(charset).length);
        for (int i = 0; i < 9; i++) {
            str += "Hello World!";
        }
        System.out.println("str bytes length = " + str.getBytes(charset).length);

        try (OutputStream os = new FileOutputStream("tmp/hello.txt")) {
            byte[] bytes = str.getBytes(charset);

            int bufferSize = 12;
            int now = 0;
            byte[] buffer = new byte[bufferSize];
            for (byte b : bytes) {

                if (now == bufferSize) {
                    os.write(buffer);
                    now = 0;
                }

                buffer[now] = b;
                now++;
            }

            for (byte b : bytes) {
                os.write(b);
            }
        }


//        try (InputStream in = new FileInputStream("tmp/hello.txt")) {
//            int c = 0;
//            while ((c = in.read()) != -1) {
//                System.out.print((char) c);
//            }
//        }
    }
}
