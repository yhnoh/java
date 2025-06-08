package org.example.io.nio;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class FileChannelMain1 {

    public static void main(String[] args) throws IOException {

        Path path = Path.of("tmp/nio/filechannel.txt");
        Files.deleteIfExists(path);
        Files.createDirectories(path.getParent());
        Files.createFile(path);

        String content = "안녕하세요. NIO FileChannel을 사용한 파일 쓰기 예제입니다.\n";


//        writeToFileChannel2(path, content);
        writeToFileChannel3(path, content, 3);

    }

    private static void writeToFileChannel1(Path path, String content) throws IOException {
        ByteBuffer byteBuffer = ByteBuffer.wrap(content.getBytes(StandardCharsets.UTF_8));
        try (FileChannel channel = FileChannel.open(path, StandardOpenOption.WRITE)) {
            while (byteBuffer.hasRemaining()) {
                channel.write(byteBuffer);
            }
        }
    }

    private static void writeToFileChannel2(Path path, String content) throws IOException {

        ByteBuffer byteBuffer = ByteBuffer.wrap(content.getBytes(StandardCharsets.UTF_8));
        try (FileOutputStream outputStream = new FileOutputStream(path.toFile());
             FileChannel channel = outputStream.getChannel()) {

            while (byteBuffer.hasRemaining()) {
                channel.write(byteBuffer);
            }
        }
    }


    private static void writeToFileChannel3(Path path, String content, int bufferCapacity) throws IOException {
        try (FileChannel channel = FileChannel.open(path, StandardOpenOption.WRITE)) {

            ByteBuffer byteBuffer = ByteBuffer.allocate(bufferCapacity);
            System.out.println("[ByteBuffer 초기화] position = " + byteBuffer.position() + " limit = " + byteBuffer.limit() + " capacity = " + byteBuffer.capacity());
            byte[] contentBytes = content.getBytes(StandardCharsets.UTF_8);

            int totalWriteLength = 0;
            int remainLength = contentBytes.length;
            int putOffset = 0;
            while (remainLength > 0) {

                int putLength = Math.min(remainLength, byteBuffer.capacity());


                byteBuffer.put(contentBytes, putOffset, putLength);
                System.out.println("[ByteBuffer put] position = " + byteBuffer.position() + " limit = " + byteBuffer.limit() + " capacity = " + byteBuffer.capacity());

                byteBuffer.flip();
                System.out.println("[ByteBuffer flip] position = " + byteBuffer.position() + " limit = " + byteBuffer.limit() + " capacity = " + byteBuffer.capacity());

                channel.write(byteBuffer);
                System.out.println("[FileChannel write] position = " + byteBuffer.position() + " limit = " + byteBuffer.limit() + " capacity = " + byteBuffer.capacity());

                byteBuffer.clear();
                System.out.println("[ByteBuffer clear] position = " + byteBuffer.position() + " limit = " + byteBuffer.limit() + " capacity = " + byteBuffer.capacity());

                totalWriteLength += totalWriteLength;
                remainLength -= putLength;
                putOffset += putLength;
            }
        }
    }
}
