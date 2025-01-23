package org.example.io.encoding;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 * 컴퓨터는 0과1만을 인지할 수 있기 때문에, 사람이 이해하고 있는 문자를 컴퓨터가 이해할 수 없다.
 * 때문에 사람의 언어를 컴퓨터가 인지하기 위하여 여러 규칙을 만들고 해당 규칙에 따라서 바이트로 변환해주는 과정을 통해서 컴퓨터가 문자를 인지할 수 있도록하였다.
 * 위와같은 과정을 인코딩 디코딩 과정이라 한다.
 *
 * 인코딩: 문자(언어)를 컴퓨터가 이해할 수 있는 바이트로 변환
 * 디코딩: 바이트 문자를 문자(언어)로 변환
 */
public class EncodingTest {


    public static void main(String[] args) {
        // 인코딩 테스트
        String str = "안녕하세요";
        // System Default
        byte[] defaultEncoding = str.getBytes(StandardCharsets.UTF_8);
        System.out.printf("%sbyte: %s\n", defaultEncoding.length, Arrays.toString(defaultEncoding));
        // ISO_8859_1
        byte[] iso8859Encoding = str.getBytes(StandardCharsets.ISO_8859_1);
        System.out.printf("%sbyte %s\n", iso8859Encoding.length, Arrays.toString(iso8859Encoding));
        // UTF_16
        byte[] utf16Encoding = str.getBytes(StandardCharsets.UTF_16BE);
        System.out.printf("%sbyte %s\n", utf16Encoding.length, Arrays.toString(utf16Encoding));


        // 디코딩 테스트
        String defaultDecoding = new String(defaultEncoding, StandardCharsets.UTF_8);
        System.out.println("defaultDecoding = " + defaultDecoding);
        String iso8859Decoding = new String(iso8859Encoding, StandardCharsets.ISO_8859_1);
        System.out.println("iso8859Decoding = " + iso8859Decoding);
        String utf16Decoding = new String(utf16Encoding, StandardCharsets.UTF_16BE);
        System.out.println("utf16Decoding = " + utf16Decoding);


    }
}
