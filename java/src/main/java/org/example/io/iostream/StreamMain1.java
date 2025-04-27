package org.example.io.iostream;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class StreamMain1 {

    public static void main(String[] args) throws IOException {

        Charset charset = StandardCharsets.UTF_8;
        String str = "Hello World!";
        //문자열 -> 바이트 -> 쓰기
        try (OutputStream os = new FileOutputStream("tmp/hello.txt")) {
            os.write(str.getBytes(charset));
        }

        //바이트 -> 문자열 -> 읽기
        try (InputStream in = new FileInputStream("tmp/hello.txt")) {
            System.out.println(new String(in.readAllBytes(), charset));
        }

    }
}
