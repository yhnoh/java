package org.example.io.iostream;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * ByteStream을 사용하는 예제
 * 1 바이트씩 읽고 쓸 수 있는 InputStream과 OutputStream을 사용
 */
public class ByteStreamMain1 {

    public static void main(String[] args) throws IOException {

        Charset charset = StandardCharsets.UTF_8;
        String str = "Hello World!";
        //문자열 -> 바이트 -> 쓰기
        try (OutputStream os = new FileOutputStream("tmp/hello.txt")) {
            byte[] bytes = str.getBytes(charset);
            for (byte b : bytes) {
                os.write(b);
            }
        }

        //바이트 -> 문자열 -> 읽기
        try (InputStream in = new FileInputStream("tmp/hello.txt")) {
            int c = 0;
            while ((c = in.read()) != -1) {
                System.out.print((char) c);
            }
        }

    }
}
