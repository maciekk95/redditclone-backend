package com.example.redditclone.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@Slf4j
public class EmailService {

    private JavaMailSender mailSender;

    public void sendMail(String from, String to, String subject, String text) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text);
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            log.error("Failed to send email for: " + to + "\n" + e);
            throw new IllegalArgumentException("Failed to send email for: " + to);
        }
    }
}
