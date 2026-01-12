package org.example.oop.abstraction;

public class MessageService {

    private final MessageSender messageSender;

    public MessageService(MessageSender messageSender) {
        this.messageSender = messageSender;
    }

    public void sendMessage(String recipient, String message) {
        messageSender.sendMessage(recipient, message);
    }
}
