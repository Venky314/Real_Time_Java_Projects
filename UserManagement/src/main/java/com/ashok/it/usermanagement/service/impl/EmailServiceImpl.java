package com.ashok.it.usermanagement.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.ashok.it.usermanagement.service.EmailService;

@Service
public class EmailServiceImpl implements EmailService {
    
    @Autowired
    private JavaMailSender mailSender;
    
    @Override
    public boolean sendRegistrationEmail(String toEmail, String userName, String tempPassword) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(toEmail);
            message.setSubject("Welcome to User Management System");
            message.setText("Dear " + userName + ",\n\n" +
                          "Your account has been successfully created.\n" +
                          "Your temporary password is: " + tempPassword + "\n" +
                          "Please login and reset your password.\n\n" +
                          "Thank you,\n" +
                          "User Management Team");
            
            mailSender.send(message);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public boolean sendPasswordResetEmail(String toEmail, String userName) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(toEmail);
            message.setSubject("Password Reset Confirmation");
            message.setText("Dear " + userName + ",\n\n" +
                          "Your password has been successfully reset.\n" +
                          "If you did not perform this action, please contact support immediately.\n\n" +
                          "Thank you,\n" +
                          "User Management Team");
            
            mailSender.send(message);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
