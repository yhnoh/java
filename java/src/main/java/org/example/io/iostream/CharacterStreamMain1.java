package org.example.io.iostream;

import java.io.*;

public class CharacterStreamMain1 {

    public static void main(String[] args) throws IOException {

        //문자열 -> 바이트 -> 쓰기
        String path = "tmp/character.txt";
        try (Writer writer = new FileWriter(path)) {
            writer.write("Hello World!");
        }

        //바이트 -> 문자열 -> 읽기
        try (Reader reader = new FileReader(path)) {
            int c;
            while ((c = reader.read()) != -1) {
                System.out.print((char) c);
            }
        }
    }
}
