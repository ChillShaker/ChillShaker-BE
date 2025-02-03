package com.ducnt.chillshaker.service.interfaces;

import jakarta.mail.MessagingException;

public interface IEmailService {
    void sendOtp(String toEmail) throws MessagingException;
}
