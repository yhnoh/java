package org.example.io.fileio;

import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PathMain1 {

    public static void main(String[] args) {

        //Path 인스턴스 생성
        Path path = Paths.get("src/main/java/org/example/io/iofile");

        //경로 정보 탐색 메서드
        System.out.printf("toString =  %s%n", path);
        System.out.printf("getFileName =  %s%n", path.getFileName());   //
        System.out.printf("getName(0) =  %s%n", path.getName(0));   //인덱스를 통한 경로 가져오기
        System.out.printf("getNameCount =  %d%n", path.getNameCount()); //경로 Count
        System.out.printf("subpath(0,2) =  %s%n", path.subpath(0, 2));  //
        System.out.printf("getParent =  %s%n", path.getParent());   //상위 경로
        System.out.printf("getRoot =  %s%n", path.getRoot());

        //Path Converting
        System.out.printf("toAbsolutePath = %s%n", path.toAbsolutePath());  //절대 경로
        System.out.printf("toUri = %s%n", path.toUri());    //브라우저 탐색 경로

        //toRealPath의 경우 존재하는
        try {
            Path path2 = Paths.get("src/main/java/org/example/io/iofile");
            System.out.printf("toRealPath = %s\n", path2.toRealPath());
        } catch (NoSuchFileException e) {
            //파일을 찾을 수 없을 경우 에러 발생
            System.err.printf("toRealPath, no such file exception \n", path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //Joining Two Path
        Path resolved = Paths.get("foo").resolve("var");
        System.out.println("resolved = " + resolved);

        //Creating a Path Between Two Paths
        Path path1 = Paths.get("joe");
        Path path2 = Paths.get("sally");

        Path relativize1 = path1.relativize(path2);
        Path relativize2 = path2.relativize(path1);
        System.out.println("relativize1 = " + relativize1);
        System.out.println("relativize2 = " + relativize2);

        //Comparing Tow Path
    }
}
