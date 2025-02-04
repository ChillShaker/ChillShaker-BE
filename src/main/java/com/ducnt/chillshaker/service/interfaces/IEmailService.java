package com.ducnt.chillshaker.service.interfaces;

import com.ducnt.chillshaker.model.Payment;
import jakarta.mail.MessagingException;

public interface IEmailService {
    void sendOtp(String toEmail) throws MessagingException;
    void sendPaymentInfo(Payment payment) throws Exception;
}
