package org.example.io.fileio;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;

/**
 * https://docs.oracle.com/javase/tutorial/essential/io/dirs.html#create
 * https://docs.oracle.com/javase/tutorial/essential/io/file.html#creating
 * tmp/fileio 를 삭제하고 실행하는 것을 권장
 */
public class CreateFile1 {

    public static void main(String[] args) throws IOException {

        //디렉토리 생성
        try {
            Path path = Path.of("tmp/fileio");
            Path createdPath = Files.createDirectory(path);
            System.out.println("createDirectory Path = " + createdPath.toAbsolutePath());
        } catch (FileAlreadyExistsException e) {
            System.out.println("이미 존재하는 디렉토리입니다.");
        }

        //파일 생성
        try {
            Path path = Path.of("tmp/fileio/createFile.txt");
            Path createdPath = Files.createFile(path);
            System.out.println("createDirectory Path = " + createdPath.toAbsolutePath());
        } catch (NoSuchFileException e) {
            System.out.println("디렉토리가 존재하지 않습니다.");
        } catch (FileAlreadyExistsException e) {
            System.out.println("이미 존재하는 파일입니다.");
        }

        //임시 디렉토리 생성
        try {
            //사용자가 지정한 경로에 접두사로 시작하는 새 디렉토리를 생성
            Path path = Path.of("tmp/fileio/tmp");
            Files.createDirectory(path);
            Path userCreatedPath = Files.createTempDirectory(path, "tmp");
            System.out.println("user createTempDirectory Path = " + userCreatedPath.toAbsolutePath());

            //파일 시스템에 지정된 임시 디렉토리에서 접두사로 시작하는 새 디렉토리를 생성
            Path systemCreatedPath = Files.createTempDirectory("tmp");
            System.out.println("system createTempDirectory Path = " + systemCreatedPath.toAbsolutePath());
            Files.delete(systemCreatedPath);
        } catch (FileAlreadyExistsException e) {
            System.out.println("이미 존재하는 디렉토리입니다.");
        }

        //임시 파일 생성
        try {
            //사용자가 지정한 경로에 접두사와 접미사로 시작하는 새 파일을 생성
            Path usetCreatedPath = Files.createTempFile(Path.of("tmp/fileio/tmp"), "createTempFile", ".txt");
            System.out.println("user createTempFile Path = " + usetCreatedPath.toAbsolutePath());

            //파일 시스템에 지정된 임시 디렉토리에서 접두사와 접미사로 시작하는 새 파일을 생성
            Path systemCreatedPath = Files.createTempFile("createTempFile", ".txt");
            System.out.println("system createTempFile Path = " + systemCreatedPath.toAbsolutePath());
            Files.delete(systemCreatedPath);
        } catch (FileAlreadyExistsException e) {
            System.out.println("이미 존재하는 파일입니다.");
        }
    }
}
