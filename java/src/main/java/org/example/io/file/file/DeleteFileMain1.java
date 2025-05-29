package org.example.io.file.file;

import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;

public class DeleteFileMain1 {

    public static void main(String[] args) throws IOException {
        Path directoryPath = Path.of("tmp/fileio/delete");
        Path filePath = directoryPath.resolve("deleteFile.txt");

        Files.createDirectories(directoryPath);
        Files.createFile(filePath);

        // 파일 및 디렉터리가 존재하는 경우 파일 및 디렉터리 삭제
        Files.delete(filePath);
        Files.delete(directoryPath);

        // 해당 파일 및 디렉터리가 존재하지 않는 경우 NoSuchFileException 발생
        Path notExistDirectoryPath = directoryPath.resolve("tmp/fileio/notExistDirectory");
        Path notExistFile = notExistDirectoryPath.resolve("notExistFile.txt");

        try {
            Files.delete(notExistDirectoryPath);
        } catch (NoSuchFileException e) {
            System.out.println("Files.delete() 디렉토리가 존재하지 않습니다. => " + e);
        }

        try {
            Files.delete(notExistFile);
        } catch (NoSuchFileException e) {
            System.out.println("Files.delete() 파일이 존재하지 않습니다. => " + e);
        }

        // 디렉터리내에 파일이 존재하는데 디렉터리를 삭제하려는 경우 DirectoryNotEmptyException 발생
        try {
            Files.createDirectories(directoryPath);
            Files.createFile(filePath);

            Files.delete(directoryPath);
        } catch (DirectoryNotEmptyException e) {
            System.out.println("Files.delete() 디렉토리 내에 파일이 존재합니다. => " + e);
            Files.delete(filePath);
            Files.delete(directoryPath);

        }


        // deleteIfExists의 경우 해당 파일 및 디렉토리가 존재하지 않는 경우에도 NoSuchFileException 발생하지 않음
        // 여러 스레드가 파일을 삭제하는 경우 예외를 발생시키지 않고자 할때 유용
        Files.deleteIfExists(notExistDirectoryPath);
        Files.deleteIfExists(notExistFile);

        // deleteIfExists 또한 디렉터리내에 파일이 존재하는데 디렉터리를 삭제하려는 경우 DirectoryNotEmptyException 발생
        try {
            Files.createDirectories(directoryPath);
            Files.createFile(filePath);

            Files.deleteIfExists(directoryPath);
        } catch (DirectoryNotEmptyException e) {
            System.out.println("Files.deleteIfExists() 디렉토리 내에 파일이 존재합니다. => " + e);
            Files.deleteIfExists(filePath);
            Files.deleteIfExists(directoryPath);
        }


    }
}
