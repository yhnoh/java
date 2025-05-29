package org.example.io.stream;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * <p>바이트 배열을 이용한 I/O 예제<p/>
 * <br/>
 * <p>write(byte[] b, int off, int len) throws IOException <p/>
 * b : 데이터 쓰기를 위한 바이트 배열 <br/>
 * off : b 배열의 시작 인덱스 <br/>
 * len : b 배열을 어디까지 읽을지의 길이 <br/>
 *
 * <p> read(byte[] b, int off, int len) </p>
 * b : 데이터 읽기를 위한 바이트 배열 <br/>
 * off : b 배열의 시작 인덱스 <br/>
 * len : b 배열을 어디까지 읽을지의 길이 <br/>
 * return: 읽은 바이트 길이를 리턴한다. 만약 읽은 바이트가 없으면 -1을 리턴한다.
 */
public class ByteStreamMain2 {

    public static void main(String[] args) throws IOException {

        String path = "tmp/byte.txt";
        Charset charset = StandardCharsets.UTF_8;
        String str = "Hello World!!!!";
        byte[] outBytes = str.getBytes(charset);
        //문자열 -> 바이트 -> 쓰기
        try (FileOutputStream os = new FileOutputStream(path)) {

            os.write(outBytes, 0, outBytes.length);
        }

        //바이트 -> 문자열 -> 읽기
        try (FileInputStream in = new FileInputStream(path)) {
            //outBytes.length 만큼 배열을 생성하여 데이터 읽기
            byte[] inBytes = new byte[outBytes.length];

            int readBytesLength;
            while ((readBytesLength = in.read(inBytes, 0, inBytes.length)) != -1) {
                System.out.print(new String(inBytes, 0, readBytesLength));
            }
        }
    }
}
