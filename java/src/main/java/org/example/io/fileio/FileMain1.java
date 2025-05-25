package org.example.io.fileio;

import java.io.*;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.*;

public class FileMain1 {

    public static void main(String[] args) throws IOException {

        //Creating a File
        try {
            Path path = Paths.get("tmp/createFile.txt");
            Files.createFile(path);
        } catch (FileAlreadyExistsException e) {
            System.out.println("throw FileAlreadyExistsException: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("throw IOException: " + e.getMessage());
        }

        //파일에 읽기 및 쓰기 작업
        try {
            Path path = Paths.get("tmp/createFile.txt");
            String text = "Hello World!";

            // Stream I/O 사용하기
            try (OutputStream os = Files.newOutputStream(path)) {

                System.out.println("write by OutputStream");
                os.write(text.getBytes());
            }

            try (InputStream is = Files.newInputStream(path)) {
                String s = new String(is.readAllBytes());
                System.out.println("read by InputStream = " + s);
            }

            // Bufferd Stream I/O 사용하기
            try (BufferedWriter writer = Files.newBufferedWriter(path);
                 BufferedReader reader = Files.newBufferedReader(path)) {
                System.out.println("write by BufferedWriter");
                writer.write(text);

                String s;
                while ((s = reader.readLine()) != null) {
                    System.out.println("read By BufferedReader = " + s);
                }

            }

            try (BufferedReader reader = Files.newBufferedReader(path)) {
                String s;
                while ((s = reader.readLine()) != null) {
                    System.out.println("read By BufferedReader = " + s);
                }
            }

            // 파일 유틸클래스에서 write 메서드를 사용한 파일 쓰기
            System.out.println("write by Files.write");
            Files.write(path, text.getBytes());
            System.out.println("read by Files.readAllBytes = " + new String(Files.readAllBytes(path)));

            System.out.println("write by Files.writeByString");
            Files.writeString(path, text);
            Files.readAllLines(path).forEach(str -> {
                System.out.println("readBy by Files.readAllLines = " + str);
            });

        } catch (IOException e) {
            System.out.println("throw IOException: " + e.getMessage());
        }

        /**
         * Checking File a Directory
         * //Checking https://docs.oracle.com/javase/tutorial/essential/io/check.html
         */
        Path path = Paths.get("tmp/createFile.txt");
        System.out.println("Files.exists = " + Files.exists(path));
        System.out.println("Files.notExists = " + Files.notExists(path));
        System.out.println("Files.isReadable = " + Files.isReadable(path));
        System.out.println("Files.isWritable = " + Files.isWritable(path));
        System.out.println("Files.isExecutable = " + Files.isExecutable(path));
        try {
            System.out.println("Files.isExecutable = " + Files.isSameFile(path, path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        /**
         * Managing Metadata
         * https://docs.oracle.com/javase/tutorial/essential/io/fileAttr.html
         */
        System.out.println("Files.size = " + Files.size(path));
        System.out.println("Files.isDirectory = " + Files.isDirectory(path));
        System.out.println("Files.isRegularFile = " + Files.isRegularFile(path));
        System.out.println("Files.isSymbolicLink = " + Files.isSymbolicLink(path));
        System.out.println("Files.isHidden = " + Files.isHidden(path));
        System.out.println("Files.getLastModifiedTime = " + Files.getLastModifiedTime(path));
        System.out.println("Files.getOwner = " + Files.getOwner(path));
        System.out.println("Files.getPosixFilePermissions = " + Files.getPosixFilePermissions(path));

        /**
         * Moving a File Or Directory
         * https://docs.oracle.com/javase/tutorial/essential/io/move.html
         */
        Files.move(path, Paths.get("tmp/createFileMove.txt"));
    }

    public static void readFile(Path path) throws IOException {

        // Files.newByteChannel() defaults to StandardOpenOption.READ
        try (SeekableByteChannel sbc = Files.newByteChannel(path)) {
            final int BUFFER_CAPACITY = 8 * 1024;
        }
    }

    public static void deleteFile(Path path) {
        try {
            Files.delete(path);
        } catch (NoSuchFileException e) {

        } catch (DirectoryNotEmptyException e) {

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

