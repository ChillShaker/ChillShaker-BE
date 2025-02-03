package com.ducnt.chillshaker.service.thirdparty;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class EmailService implements com.ducnt.chillshaker.service.interfaces.IEmailService {
    JavaMailSender mailSender;
    Random random = new Random();
    RedisService redisService;

    static String htmlContext = "";

    @Override
    public void sendOtp(String toEmail) throws MessagingException {
        String otp = String.format("%06d", random.nextInt(999999));
        String savedOtp = redisService.getOTP(toEmail);
        if(savedOtp != null) {
            redisService.deleteOTP(toEmail);
        }
        redisService.saveOTP(toEmail, otp, 5);
        SimpleMailMessage mimeMessageHelper = new SimpleMailMessage();
        mimeMessageHelper.setTo(toEmail);
        mimeMessageHelper.setSubject("Your OTP Code");
        mimeMessageHelper.setText("Your OTP code is: " + otp + ". It is valid for 5 minutes.");
        mailSender.send(mimeMessageHelper);
    }

}
