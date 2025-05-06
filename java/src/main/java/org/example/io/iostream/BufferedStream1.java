package org.example.io.iostream;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * Bufferd Stream을 이용한 Byte Stream 예제
 */
public class BufferedStream1 {

    private static final int DEFAULT_MAX_BUFFER_SIZE = 8192;

    public static void main(String[] args) throws IOException {


        String path = "tmp/buffed.txt";
        Charset charset = StandardCharsets.UTF_8;
        String str = "Hello World!";
        //문자열 -> 바이트 -> 지정한 버퍼 사이즈 만큼 버퍼에 담기 -> 쓰기
        try (BufferedOutputStream os = new BufferedOutputStream(new FileOutputStream(path), DEFAULT_MAX_BUFFER_SIZE)) {
            os.write(str.getBytes(charset));
        }

        //바이트 -> 지정한 버퍼 사이즈 만큼 버퍼에 담기 -> 문자열 -> 읽기
        try (BufferedInputStream in = new BufferedInputStream(new FileInputStream(path), DEFAULT_MAX_BUFFER_SIZE)) {
            int c;
            while ((c = in.read()) != -1) {
                System.out.print((char) c);
            }
        }

    }
}
