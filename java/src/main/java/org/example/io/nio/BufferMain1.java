package org.example.io.nio;

import java.nio.ByteBuffer;

public class BufferMain1 {

    public static void main(String[] args) {


        byte[] bytes = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j'};
        // ByteBuffer 초기화
        ByteBuffer byteBuffer = ByteBuffer.allocate(bytes.length);
        System.out.println("ByteBuffer 초기화");
        System.out.println("capacity = " + byteBuffer.capacity() + ", limit = " + byteBuffer.limit() + ", position = " + byteBuffer.position() + ", remaining = " + byteBuffer.remaining());
        System.out.println();

        // ByteBuffer 데이터 쓰기, 원본의 offset 부터 지정한 길이 만큼 ByteBuffer에 데이터를 쓴다.
        byteBuffer.put(bytes, 0, bytes.length);
        System.out.println("ByteBuffer 데이터 쓰기");
        System.out.println("capacity = " + byteBuffer.capacity() + ", limit = " + byteBuffer.limit() + ", position = " + byteBuffer.position() + ", remaining = " + byteBuffer.remaining());
        System.out.println();

        // ByteBuffer 데이를 읽거나 쓰기위하여 준비, limit를 position으로 설정하고, position을 0으로 설정한다.
        byteBuffer.flip();
        System.out.println("ByteBuffer 데이를 읽거나 쓰기위하여 준비");
        System.out.println("capacity = " + byteBuffer.capacity() + ", limit = " + byteBuffer.limit() + ", position = " + byteBuffer.position() + ", remaining = " + byteBuffer.remaining());
        System.out.println();

        // ByteBuffer 데이터 읽기
        System.out.println("ByteBuffer 데이터 읽기");
        while (byteBuffer.hasRemaining()) {
            char c = (char) byteBuffer.get();
            System.out.println("char = " + c + ", capacity = " + byteBuffer.capacity() + ", limit = " + byteBuffer.limit() + ", position = " + byteBuffer.position() + ", remaining = " + byteBuffer.remaining());
        }
        System.out.println();

        // ByteBuffer Clear, 실제 데이터가 지워지지지는 않는다.
        byteBuffer.clear();
        System.out.println("ByteBuffer Clear");
        System.out.println("capacity = " + byteBuffer.capacity() + ", limit = " + byteBuffer.limit() + ", position = " + byteBuffer.position() + ", remaining = " + byteBuffer.remaining());
        System.out.println();

    }
}
