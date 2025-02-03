package com.ducnt.chillshaker.config.thirdParty;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailConfig {

    @Value("${spring.mail.host}")
    private String SPRING_MAIL_HOST;

    @Value("${spring.mail.port}")
    private String SPRING_MAIL_PORT;

    @Value("${spring.mail.username}")
    private String SPRING_MAIL_USERNAME;

    @Value("${spring.mail.password}")
    private String SPRING_MAIL_PASSWORD;

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost(SPRING_MAIL_HOST);
        javaMailSender.setPort(Integer.parseInt(SPRING_MAIL_PORT));
        javaMailSender.setUsername(SPRING_MAIL_USERNAME);
        javaMailSender.setPassword(SPRING_MAIL_PASSWORD);

        Properties props = javaMailSender.getJavaMailProperties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");

        return javaMailSender;
    }
}
