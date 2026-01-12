package org.example.oop.abstraction;

public class EmailSender implements MessageSender {
    @Override
    public void sendMessage(String recipient, String message) {
        // Email 보내는 로직 작성
        System.out.println("Sending Email to " + recipient + ": " + message);
    }
}
