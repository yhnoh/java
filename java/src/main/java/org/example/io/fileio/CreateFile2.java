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
public class CreateFile2 {

    public static void main(String[] args) throws IOException {


        Path path1 = Path.of("tmp/fileio/createFile1.txt");
        Path path2 = Path.of("tmp/fileio/createFile2.txt");

        Files.deleteIfExists(path1);
        FileAttribute<Set<PosixFilePermission>> fileAttribute1 = PosixFilePermissions.asFileAttribute(Set.of(PosixFilePermission.OWNER_READ,
                PosixFilePermission.OWNER_WRITE,
                PosixFilePermission.OWNER_EXECUTE));
        Files.createFile(path1, fileAttribute1);

        Files.deleteIfExists(path2);
        FileAttribute<Set<PosixFilePermission>> fileAttribute2 = PosixFilePermissions.asFileAttribute(PosixFilePermissions.
                fromString("r-x------"));
        Files.createFile(path2, fileAttribute2);
    }
}
