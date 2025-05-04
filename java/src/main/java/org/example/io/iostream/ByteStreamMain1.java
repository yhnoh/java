package org.example.io.iostream;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * <p>히나의 바이트를 이용한 I/O <p/>
 */
public class ByteStreamMain1 {

    public static void main(String[] args) throws IOException {

        String path = "tmp/byte.txt";
        Charset charset = StandardCharsets.UTF_8;
        String str = "Hello World!";
        //문자열 -> 바이트 -> 쓰기
        try (OutputStream os = new FileOutputStream(path)) {
            byte[] bytes = str.getBytes(charset);
            for (byte b : bytes) {
                os.write(b);
            }
        }

        //바이트 -> 문자열 -> 읽기
        try (InputStream in = new FileInputStream(path)) {
            int c = 0;
            while ((c = in.read()) != -1) {
                System.out.print((char) c);
            }
        }
    }
}
