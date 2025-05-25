package org.example.io.fileio;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.PosixFilePermissions;


/**
 * https://docs.oracle.com/javase/tutorial/essential/io/dirs.html
 * https://docs.oracle.com/javase/tutorial/essential/io/file.html
 * https://docs.oracle.com/javase/tutorial/essential/io/delete.html
 */
public class FileMain2 {

    public static void main(String[] args) throws IOException {

        //Creating Directory
        Path path = Paths.get("tmp/fileio");
        Path path1 = Files.createDirectories(path);
        PosixFilePermissions.asFileAttribute(
                PosixFilePermissions.fromString("rwxr-xr-x")); // rwxr-xr-x 권한 설정

        Path path2 = Paths.get("tmp/fileio/foo/bar");
        Path path3 = Files.createDirectories(path2);
        Files.isSameFile(path2, path3);

        Files.createDirectories(Paths.get("tmp/fileio/tmp"));

    }
}
