package org.example.io.iostream;

import java.io.*;

public class BufferedStream2 {

    private static final int DEFAULT_MAX_BUFFER_SIZE = 8 * 1024; // 8KB

    public static void main(String[] args) throws IOException {

        String path = "tmp/buffed.txt";

        //문자열 -> 바이트 -> 지정한 버퍼 사이즈 만큼 버퍼에 담기 -> 쓰기
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path), DEFAULT_MAX_BUFFER_SIZE)) {
            writer.write("Hello World!");
        }

        //바이트 -> 지정한 버퍼 사이즈 만큼 버퍼에 담기 -> 문자열 -> 읽기
        try (BufferedReader reader = new BufferedReader(new FileReader(path), DEFAULT_MAX_BUFFER_SIZE)) {
            int c;
            while ((c = reader.read()) != -1) {
                System.out.print((char) c);
            }
        }

    }
}
