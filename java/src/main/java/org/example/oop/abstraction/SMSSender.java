package org.example.oop.abstraction;

public class SMSSender implements MessageSender {

    @Override
    public void sendMessage(String recipient, String message) {
        // SMS 보내는 로직 작성
        System.out.println("Sending SMS to " + recipient + ": " + message);
    }
}
