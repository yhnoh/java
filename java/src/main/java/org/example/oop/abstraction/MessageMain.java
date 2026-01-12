package org.example.oop.abstraction;

public class MessageMain {

    public static void main(String[] args) {
        // Email 메시지 보내기
        MessageSender emailSender = new EmailSender();
        MessageService emailService = new MessageService(emailSender);
        emailService.sendMessage("user1", "Hello World");
    }
}
