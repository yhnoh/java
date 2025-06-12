package org.example.io.nio;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;


public class ServerMain1 {

    private static final Logger log = LoggerFactory.getLogger(ServerMain1.class);


    private static final int PORT = 8080;
    private static final int BUFFER_SIZE = 1024;
    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

    public static void main(String[] args) throws IOException {


        Selector selector = null;
        ServerSocketChannel serverChannel = null;
        try {

            selector = Selector.open();
            serverChannel = ServerSocketChannel.open();

            serverChannel.configureBlocking(false); //서버 소켓 채널 비동기 모드 설정
            serverChannel.socket().bind(new InetSocketAddress(PORT)); // 서버 지정된 포트에 바인딩

            serverChannel.register(selector, SelectionKey.OP_ACCEPT); // 클라이언트 연결 요청에 대한 SelectionKey 등록

            log.info("[서버] 서버 시작... 포트 {}", PORT);
            while (true) {

                // Selector를 통해서 이벤트 기다림
                log.info("[서버] 클라이언트 연결 기다림....");
                int select = selector.select();
                if (select == 0) {
                    continue;
                }

                // Selector에서 선택된 키를 가져옴
                Iterator<SelectionKey> selectKeyIterator = selector.selectedKeys().iterator();

                while (selectKeyIterator.hasNext()) {
                    SelectionKey selectionKey = selectKeyIterator.next();

                    if (selectionKey.isAcceptable()) {

                        SocketChannel clientChannel = serverChannel.accept();
                        clientChannel.configureBlocking(false);
                        clientChannel.register(selector, SelectionKey.OP_READ);
                        log.info("[서버] 클라이언트 연결 주소 = {}", clientChannel.getRemoteAddress());
                    } else if (selectionKey.isReadable()) {
                        SocketChannel clientChannel = null;
                        try {
                            clientChannel = (SocketChannel) selectionKey.channel();
                            ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
                            int read = clientChannel.read(buffer);
                            if (read > 0) {
                                buffer.flip();

                                // 초기에 할당한 버퍼에서 필요한 부분만 데이터를 읽기
                                byte[] data = new byte[buffer.remaining()];
                                buffer.get(data);
                                log.info("[서버] 클라이언트({})가 보낸 데이터 = {}", clientChannel.getRemoteAddress(),
                                        new String(data, DEFAULT_CHARSET));
                                buffer.clear();
                            }


                        } finally {
                            log.info("[서버] 클라이언트({}) 연결 종료", clientChannel.getRemoteAddress());
                            if (clientChannel != null && clientChannel.isOpen()) {
                                clientChannel.close();
                            }
                        }
                    }

                    selectKeyIterator.remove();
                }
            }

        } finally {
            if (serverChannel != null && serverChannel.isOpen()) {
                serverChannel.close();
            }

            if (selector != null && selector.isOpen()) {
                selector.close();
            }
        }
    }
}
