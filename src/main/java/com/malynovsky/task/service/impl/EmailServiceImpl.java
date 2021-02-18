package com.malynovsky.task.service.impl;

import com.malynovsky.task.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender emailSender;

    @Autowired
    public EmailServiceImpl(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    @Async
    @Override
    public void sendToken(String email, String token) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("randomuser.346742378@gmail.com");
        message.setTo(email);
        message.setSubject("Use you token");
        message.setText(token);

        emailSender.send(message);
    }
}
