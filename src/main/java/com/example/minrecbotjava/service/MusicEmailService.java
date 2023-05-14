package com.example.minrecbotjava.service;

import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.FileNotFoundException;


public interface MusicEmailService {
    void sendSimpleEmail(String toAddress, String subject, String message);

    void sendEmailWithAttachment(String toAddress, String subject, String message, String fileName) throws MessagingException, FileNotFoundException;

    void sendEmailZip(String toAddress, String message, String fileName) throws MessagingException, FileNotFoundException;
}
