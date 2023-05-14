package com.example.minrecbotjava.service.impl;

import com.example.minrecbotjava.service.MusicEmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.FileNotFoundException;

@Service
@Slf4j
@RequiredArgsConstructor
public class MusicEmailServiceImpl implements MusicEmailService {

    private final JavaMailSender emailSender;

    @Override
    public void sendSimpleEmail(String toAddress, String subject, String message) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(toAddress);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(message);
        emailSender.send(simpleMailMessage);
    }

    @Override
    public void sendEmailWithAttachment(String toAddress, String subject, String message, String fileName) throws MessagingException, FileNotFoundException {

        MimeMessage mimeMessage = emailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
        messageHelper.setTo(toAddress);
        messageHelper.setSubject(subject);
        messageHelper.setText(message);
        FileSystemResource file = null;
        try {
            file = new FileSystemResource(ResourceUtils.getFile(fileName));
        } catch (FileNotFoundException e) {
            log.error(message);
        }
        try {
            assert file != null;
            messageHelper.addAttachment("Document" + (Math.random() * 1000000) + ".docx", file);
        } catch (MessagingException e) {
            log.error(e.getMessage());
        }
        emailSender.send(mimeMessage);
    }

    @Override
    public void sendEmailZip(String toAddress, String message,String fileName) throws MessagingException, FileNotFoundException {

        MimeMessage mimeMessage = emailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
        messageHelper.setTo(toAddress);
        messageHelper.setSubject("Архив с файлами");
        messageHelper.setText(message);
        FileSystemResource file = null;
        try {
            file = new FileSystemResource(ResourceUtils.getFile(fileName+".zip"));
        } catch (FileNotFoundException e) {
            log.error(message);
        }
        try {
            assert file != null;
            messageHelper.addAttachment(fileName+".zip", file);
        } catch (MessagingException e) {
            log.error(e.getMessage());
        }
        emailSender.send(mimeMessage);
    }
}
