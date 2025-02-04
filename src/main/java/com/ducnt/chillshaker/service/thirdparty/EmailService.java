package com.ducnt.chillshaker.service.thirdparty;

import com.ducnt.chillshaker.exception.CustomException;
import com.ducnt.chillshaker.exception.ErrorResponse;
import com.ducnt.chillshaker.model.*;
import com.ducnt.chillshaker.service.interfaces.IRedisService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Slf4j
public class EmailService implements com.ducnt.chillshaker.service.interfaces.IEmailService {
    JavaMailSender mailSender;
    Random random = new Random();
    IRedisService redisService;

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

    @Override
    public void sendPaymentInfo(Payment payment) {
        try {
            String toEmail = payment.getAccount().getEmail();
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, StandardCharsets.UTF_8.name());

            helper.setTo(toEmail);
            helper.setSubject("Your Booking Information");
            helper.setText(createBodyEmail(payment.getBooking(), payment.getTotalPrice()), true);

            mailSender.send(message);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw new CustomException(ErrorResponse.INTERNAL_SERVER);
        }
    }

    private String createBodyEmail(Booking booking, double totalPrice) {
        String barName = "Chill Shaker";
        String address = "123 Bar Street, City";
        Collection<BookingTable> bookingTables = booking.getBookingTables();
        Collection<BookingDrink> bookingDrinks = booking.getBookingDrinks();
        Collection<BookingMenu> bookingMenus = booking.getBookingMenus();

        StringBuilder htmlMessage = new StringBuilder();
        htmlMessage.append("<!DOCTYPE html>")
                .append("<html lang='vi'>")
                .append("<head>")
                .append("<meta charset='UTF-8'>")
                .append("<title>Booking BarBuddy</title>")
                .append("</head>")
                .append("<body>")
                .append("<div style='width: 600px; margin: auto; padding: 20px;'>")
                .append("<h2 style='color: #B8860B; text-align: center;'>").append(barName.toUpperCase()).append("</h2>")
                .append("<p style='text-align: center;'>").append(address).append("</p>")
                .append("<h1 style='text-align: center;'>XÁC NHẬN ĐẶT BÀN THÀNH CÔNG</h1>")
                .append("<p style='text-align: center;'>MÃ ĐẶT BÀN: <strong>").append(booking.getBookingCode()).append("</strong></p>")
                .append("<table border='1' width='100%'>")
                .append("<tr>")
                .append("<th>TÊN BÀN/ĐỒ UỐNG/MENU</th>")
                .append("<th>LOẠI BÀN/ĐỒ UỐNG/MENU</th>")
                .append("<th>ĐƠN GIÁ</th>")
                .append("<th>THÀNH TIỀN (VNĐ)</th>")
                .append("</tr>");

        for (BookingTable bookingTable : bookingTables) {
            BarTable barTable = bookingTable.getBarTable();
            TableType tableType = barTable.getTableType();
            htmlMessage.append("<tr>")
                    .append("<td>").append(barTable.getName()).append("</td>")
                    .append("<td>").append(tableType.getName()).append("</td>")
                    .append("<td>").append(tableType.getDepositAmount()).append("</td>")
                    .append("<td>").append(tableType.getDepositAmount()).append("</td>")
                    .append("</tr>");
        }

        switch (booking.getBookingType()) {
            case BOOKING_WITH_DRINK -> {
                for (BookingDrink bookingDrink: bookingDrinks) {
                    Drink drink = bookingDrink.getDrink();
                    htmlMessage.append("<tr>")
                            .append("<td>").append(drink.getName()).append("</td>")
                            .append("<td>").append(drink.getDrinkCategory().getName()).append("</td>")
                            .append("<td>").append(drink.getPrice()).append("</td>")
                            .append("<td>").append(drink.getPrice()).append("</td>")
                            .append("</tr>");
                }
            }

            case BOOKING_WITH_MENU -> {
                for (BookingMenu bookingMenu: bookingMenus) {
                    Menu menu = bookingMenu.getMenu();
                    htmlMessage.append("<tr>")
                            .append("<td>").append(menu.getName()).append("</td>")
                            .append("<td>").append("</td>")
                            .append("<td>").append(menu.getPrice()).append("</td>")
                            .append("<td>").append(menu.getPrice()).append("</td>")
                            .append("</tr>");
                }
            }
        }

        htmlMessage.append("</table>")
                .append("<p style='text-align: right; font-weight: bold;'>TỔNG TIỀN (VNĐ) - ĐÃ ÁP DỤNG GIẢM GIÁ ")
                .append(totalPrice).append("</p>")
                .append("</div>")
                .append("</body>")
                .append("</html>");

        return htmlMessage.toString();
    }
}
