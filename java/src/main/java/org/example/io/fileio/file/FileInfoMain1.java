package org.example.io.fileio.file;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;

public class FileInfoMain1 {

    public static void main(String[] args) throws IOException {

        Path directoryPath = Paths.get("tmp/fileio/fileinfo1");
        Path filePath = directoryPath.resolve("fileInfo.txt");

        // 파일이 존재하면 삭제
        Files.deleteIfExists(filePath);
        Files.deleteIfExists(directoryPath);

        // 파일 및 디렉터리 생성
        Files.createDirectories(directoryPath);
        Files.createFile(filePath);

        // 파일 및 디렉토리 존재 여부 확인
        System.out.println("Files.exists = " + Files.exists(directoryPath));
        System.out.println("Files.exists = " + Files.exists(filePath));
        System.out.println("Files.exists = " + Files.exists(Path.of("tmp/fileio/asdasd")));
        System.out.println("Files.exists = " + Files.exists(Path.of("tmp/fileio/asdasd.txt")));

        // 파일 접근 권한 확인
        System.out.println("Files.isReadable = " + Files.isReadable(filePath));
        System.out.println("Files.isWritable = " + Files.isWritable(filePath));
        System.out.println("Files.isExecutable = " + Files.isExecutable(filePath));

        // 파일 크기 확인
        Files.writeString(filePath, "Hello World!");
        System.out.println("Files.size = " + Files.size(directoryPath));
        System.out.println("Files.size = " + Files.size(filePath));
        System.out.println("Files.isDirectory = " + Files.isDirectory(directoryPath));
        System.out.println("Files.isDirectory = " + Files.isDirectory(filePath));

        // 파일 속성 정보 확인
        BasicFileAttributes basicFileAttributes = Files.readAttributes(filePath, BasicFileAttributes.class);
        System.out.println("BasicFileAttributes.creationTime = " + basicFileAttributes.creationTime());
        System.out.println("BasicFileAttributes.lastAccessTime = " + basicFileAttributes.lastAccessTime());
        System.out.println("BasicFileAttributes.lastModifiedTime = " + basicFileAttributes.lastModifiedTime());

    }
}
