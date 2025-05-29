package org.example.io.file.file;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;


/**
 * https://docs.oracle.com/javase/tutorial/essential/io/copy.html
 * https://docs.oracle.com/javase/tutorial/essential/io/move.html
 */
public class CopyFileMain1 {

    public static void main(String[] args) throws IOException {
        Path directroyPath = Path.of("tmp/fileio/copy");
        Path orgFile1 = Path.of("tmp/fileio/copy/orgFile1.txt");
        Path copyFile1 = Path.of("tmp/fileio/copy/copyFile1.txt");

        // 파일이 존재하면 삭제
        Files.deleteIfExists(copyFile1);
        Files.deleteIfExists(orgFile1);
        Files.deleteIfExists(directroyPath);

        // 디렉토리 생성
        Files.createDirectory(directroyPath);

        // 원본 파일 생성 및 내용 쓰기
        Files.createFile(orgFile1);
        Files.writeString(orgFile1, "Hello World!");

        // 원본 파일 복사
        Files.copy(orgFile1, copyFile1);

    }
}
