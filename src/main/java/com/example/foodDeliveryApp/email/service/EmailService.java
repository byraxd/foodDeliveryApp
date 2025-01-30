package com.example.foodDeliveryApp.email.service;

import jakarta.mail.MessagingException;

public interface EmailService {
    void sendSimpleEmail(String to, String subject, String text);
    void sendMessageWithAttachment(String to, String subject, String text, String filePath) throws MessagingException;
}
