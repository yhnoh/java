package org.example.io.file.file;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class MoveFileMain1 {

    public static void main(String[] args) throws IOException {
        Path directroyPath = Path.of("tmp/fileio/move");
        Path orgFile1 = Path.of("tmp/fileio/move/orgFile1.txt");
        Path moveFile1 = Path.of("tmp/fileio/move/moveFile1.txt");

        // 파일이 존재하면 삭제
        Files.deleteIfExists(moveFile1);
        Files.deleteIfExists(orgFile1);
        Files.deleteIfExists(directroyPath);

        // 디렉토리 생성
        Files.createDirectory(directroyPath);

        // 원본 파일 생성 및 내용 쓰기
        Files.createFile(orgFile1);
        Files.writeString(orgFile1, "Hello World!");

        // 원본 파일 이동
        Files.move(orgFile1, moveFile1);
    }
}
