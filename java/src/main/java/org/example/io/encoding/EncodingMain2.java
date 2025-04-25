package org.example.io.encoding;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class EncodingMain2 {
    
    public static void main(String[] args) {
        Charset charset = StandardCharsets.UTF_8;
        String str = "Hello World!";

        //인코딩
        byte[] encodedBytes = str.getBytes(charset);
        System.out.println("인코딩된 바이트 (" + charset.name() + ") = " + Arrays.toString(encodedBytes));

        //디코딩
        String decodedString = new String(encodedBytes, charset);
        System.out.println("디코딩된 바이트 (" + charset.name() + ") = " + decodedString);
    }
}
