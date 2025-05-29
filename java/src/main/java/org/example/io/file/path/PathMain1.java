package org.example.io.file.path;

import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PathMain1 {

    public static void main(String[] args) {

        //Path 인스턴스 생성
        Path path = Paths.get("./src/java");

        //경로 탐색 메서드
        System.out.printf("toString =  %s%n", path);
        System.out.printf("getFileName =  %s%n", path.getFileName());   // 마지막 인덱스에 존재하는 파일명 또는 디렉터리명
        System.out.printf("getName =  %s%n", path.getName(0));   //인덱스를 통한 경로 가져오기
        System.out.printf("getNameCount =  %d%n", path.getNameCount()); //경로 갯수
        System.out.printf("subpath =  %s%n", path.subpath(0, 2));  //인덱스를 통한 경로 추출
        System.out.printf("getParent =  %s%n", path.getParent());   //상위 경로
        System.out.printf("getRoot =  %s%n", path.getRoot());   //루트 경로, 상대 경로 지정시 null 반환

        //경로 변환
        System.out.printf("toAbsolutePath = %s%n", path.toAbsolutePath());  //절대 경로
        System.out.printf("toUri = %s%n", path.toUri());    //브라우저 탐색 경로
        //실제 존재하는 경로만 반환, 해당 경로가 존재하지 않을 경우 NoSuchFileException 발생
        //toRealPath의 경우 존재하는
        try {
            Paths.get("./src/java2").toRealPath();
        } catch (NoSuchFileException e) {
            //파일을 찾을 수 없을 경우 에러 발생
            System.out.println("toRealPath throw NoSuchFileException" + e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        /**
         * 경로 연산
         */
        //경로 결합
        System.out.println("resolve = " + Paths.get("foo").resolve("var"));
        System.out.println("resolve = " + Paths.get("foo").resolve("/var")); //resolve 메서드에 절대경로가 인자로 들어가면 절대경로만 반환

        //경로 비교

        if (Paths.get("/foo/var").equals(Paths.get("/foo/var"))) {
            System.out.println("path.equals");
        }

        if (Paths.get("/foo/var").startsWith(Paths.get("/foo"))) {
            System.out.println("path.startsWith");
        }

        if (Paths.get("/foo/var").endsWith(Paths.get("var"))) {
            System.out.println("path.endsWith");
        }

    }
}
