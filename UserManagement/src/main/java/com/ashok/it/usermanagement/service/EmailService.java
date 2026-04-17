package com.ashok.it.usermanagement.service;

public interface EmailService {
    
    boolean sendRegistrationEmail(String toEmail, String userName, String tempPassword);
    
    boolean sendPasswordResetEmail(String toEmail, String userName);
}
