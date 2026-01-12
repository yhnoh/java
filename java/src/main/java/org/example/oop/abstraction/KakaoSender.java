package org.example.oop.abstraction;

public class KakaoSender implements MessageSender {
    @Override
    public void sendMessage(String recipient, String message) {
        // KakaoTalk 보내는 로직 작성
        System.out.println("Sending KakaoTalk message to " + recipient + ": " + message);
    }
}
