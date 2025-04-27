package org.example.io.iostream;

import java.io.*;

public class StreamMain2 {

    public static void main(String[] args) throws IOException {

        //문자열 -> 바이트 -> 쓰기
        try (Writer writer = new FileWriter("tmp/hello.txt")) {
            writer.write("Hello World!");
        }

        //바이트 -> 문자열 -> 읽기
        try (Reader reader = new FileReader("tmp/hello.txt")) {
            int c;
            while ((c = reader.read()) != -1) {
                System.out.print((char) c);
            }
        }
    }
}
