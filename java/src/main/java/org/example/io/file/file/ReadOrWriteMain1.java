package org.example.io.file.file;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * https://docs.oracle.com/javase/tutorial/essential/io/file.html
 */
public class ReadOrWriteMain1 {

    public static void main(String[] args) throws IOException {

        Path path = Paths.get("tmp/createFile.txt");
        Files.deleteIfExists(path);

        Files.createFile(path);

        //파일에 읽기 및 쓰기 작업
        try {
            String text = "Hello World!";

            // Stream I/O 사용하기
            readWriteStream(path, text);
            // Bufferd Stream I/O 사용하기
            readWriteBufferedStream(path, text);
            // write 및 readAllBytes 사용하기
            readWriteStreamUtil(path, text);
            // writeString 및 readAllLines 사용하기
            readWriteCharacterStreamUtil(path, text);

        } catch (IOException e) {
            System.out.println("throw IOException: " + e.getMessage());
        }
    }


    private static void readWriteStream(Path path, String text) throws IOException {
        // Stream I/O 사용하기
        try (OutputStream os = Files.newOutputStream(path)) {
            System.out.println("write by OutputStream");
            os.write(text.getBytes());

        }

        try (InputStream is = Files.newInputStream(path)) {
            String s = new String(is.readAllBytes());
            System.out.println("read by InputStream = " + s);
        }

    }

    private static void readWriteBufferedStream(Path path, String text) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            System.out.println("write by BufferedWriter");
            writer.write(text);
        }

        try (BufferedReader reader = Files.newBufferedReader(path)) {
            String s;
            while ((s = reader.readLine()) != null) {
                System.out.println("read By BufferedReader = " + s);
            }
        }
    }

    private static void readWriteStreamUtil(Path path, String text) throws IOException {
        System.out.println("write by Files.write");
        Files.write(path, text.getBytes());
        System.out.println("read by Files.readAllBytes = " + new String(Files.readAllBytes(path)));
    }

    private static void readWriteCharacterStreamUtil(Path path, String text) throws IOException {
        System.out.println("write by Files.writeByString");
        Files.writeString(path, text);
        Files.readAllLines(path).forEach(str -> {
            System.out.println("read by Files.readAllLines = " + str);
        });
    }


}

