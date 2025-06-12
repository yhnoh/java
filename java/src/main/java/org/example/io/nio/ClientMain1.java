package org.example.io.nio;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class ClientMain1 {

    private static final Logger log = LoggerFactory.getLogger(ClientMain1.class);


    private static final String SERVER_HOSTNAME = "localhost";
    private static final int SERVER_PORT = 8080;
    private static final int CLIENT_PORT = 8081;
    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;


    public static void main(String[] args) throws IOException {


        SocketChannel clientChannel = null;
        try {
            clientChannel = SocketChannel.open();
            clientChannel.configureBlocking(false);
            boolean isConnected = clientChannel.connect(new InetSocketAddress(SERVER_HOSTNAME, SERVER_PORT));
            if (!isConnected) {
                while (!clientChannel.finishConnect()) {
                    log.info("[클라이언트] 서버 연결 대기중...");
                }
            }

            log.info("[클라이언트] 서버 연결 완료");
            log.info("[클라이언트] 서버에게 메시지 전송");
            String message = "Hello, Server!";
            ByteBuffer byteBuffer = ByteBuffer.wrap(message.getBytes(DEFAULT_CHARSET));
            clientChannel.write(byteBuffer);

        } finally {
            log.info("[클라이언트] 연결 종료");
            if (clientChannel != null && clientChannel.isOpen()) {
                clientChannel.close();
            }
        }

    }
}
