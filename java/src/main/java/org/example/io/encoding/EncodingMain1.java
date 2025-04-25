package org.example.io.encoding;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.*;
import java.util.Arrays;

public class EncodingMain1 {


    public static void main(String[] args) throws CharacterCodingException {

        Charset charset = StandardCharsets.UTF_8;
        String str = "Hello World!";
        //인코딩
        byte[] encodedBytes = getEncodedBytes(charset, str);
        System.out.println("인코딩된 바이트 (" + charset.name() + ") = " + Arrays.toString(encodedBytes));

        //디코딩
        String decodedString = getDecodedString(charset, encodedBytes);
        System.out.println("디코딩된 바이트 (" + charset.name() + ") = " + decodedString);

    }

    private static byte[] getEncodedBytes(Charset charset, String str) throws CharacterCodingException {
        CharsetEncoder charsetEncoder = charset.newEncoder();
        ByteBuffer byteBuffer = charsetEncoder.encode(CharBuffer.wrap(str));
        return byteBuffer.array();
    }

    public static String getDecodedString(Charset charset, byte[] bytes) throws CharacterCodingException {
        CharsetDecoder charsetDecoder = charset.newDecoder();
        CharBuffer charBuffer = charsetDecoder.decode(ByteBuffer.wrap(bytes));
        return charBuffer.toString();
    }

}
