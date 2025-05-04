package org.example.io.iostream;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;


/**
 * <p>버퍼를 이용한 I/O <p/>
 */
public class ByteStreamMain3 {

    private static final int DEFAULT_MAX_BUFFER_SIZE = 2;

    public static void main(String[] args) throws IOException {

        String path = "tmp/byte.txt";
        Charset charset = StandardCharsets.UTF_8;
        String str = "Hello World!Hello World!";
        byte[] bytes = str.getBytes(charset);
        System.out.println("bytes length = " + bytes.length);

        try (OutputStream os = new FileOutputStream(path)) {

            int count = 0;
            //데이터 작성을 위한 버퍼 선언
            byte[] buffer = new byte[DEFAULT_MAX_BUFFER_SIZE];
            for (byte b : bytes) {
                if (count >= buffer.length) {
                    os.write(buffer, 0, count);
                    count = 0;
                }
                buffer[count++] = b;
            }

            os.write(buffer, 0, count);
        }


        try (InputStream in = new FileInputStream(path)) {
            int readBytesLength;
            byte[] buffer = new byte[DEFAULT_MAX_BUFFER_SIZE];
            while ((readBytesLength = in.read(buffer, 0, buffer.length)) != -1) {
                System.out.print(new String(buffer, 0, readBytesLength));
            }
        }
    }
}
