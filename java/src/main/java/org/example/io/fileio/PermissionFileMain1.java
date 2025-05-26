package org.example.io.fileio;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.Set;

/**
 * 파일 권한
 * https://docs.oracle.com/javase/tutorial/essential/io/fileAttr.html#posix
 */
public class PermissionFileMain1 {

    public static void main(String[] args) throws IOException {


        Path directoryPath = Path.of("tmp/fileio/permission");
        Path path1 = Path.of("tmp/fileio/permission/permissionFile1.txt");
        Path path2 = Path.of("tmp/fileio/permission/permissionFile2.txt");
        Path path3 = Path.of("tmp/fileio/permission/permissionFile3.txt");

        //파일이 존재하면 삭제
        Files.deleteIfExists(path1);
        Files.deleteIfExists(path2);
        Files.deleteIfExists(path3);
        Files.deleteIfExists(directoryPath);

        Files.createDirectory(directoryPath);

        //파일 생성 시점에 권한 설정
        FileAttribute<Set<PosixFilePermission>> fileAttribute1 = PosixFilePermissions.asFileAttribute(Set.of(PosixFilePermission.OWNER_READ,
                PosixFilePermission.OWNER_WRITE,
                PosixFilePermission.OWNER_EXECUTE));
        Files.createFile(path1, fileAttribute1);

        FileAttribute<Set<PosixFilePermission>> fileAttribute2 = PosixFilePermissions.asFileAttribute(PosixFilePermissions.
                fromString("rwx------"));
        Files.createFile(path2, fileAttribute2);


        //기존 파일 권한 수정
        Files.createFile(path3);
        Files.setPosixFilePermissions(path3, PosixFilePermissions.fromString("r-x------"));


    }
}
