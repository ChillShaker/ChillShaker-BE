package com.ducnt.chillshaker;

import com.ducnt.chillshaker.dto.request.booking.BookingTableOnlyRequest;
import com.ducnt.chillshaker.dto.request.booking.BookingTableWithMenuRequest;
import com.ducnt.chillshaker.dto.request.booking.GenericBookingRequest;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@EnableTransactionManagement
public class ChillShakerApplication {

    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.load();
        System.setProperty("CLOUDINARY_CLOUD_NAME", dotenv.get("CLOUDINARY_CLOUD_NAME"));
        System.setProperty("CLOUDINARY_API_KEY", dotenv.get("CLOUDINARY_API_KEY"));
        System.setProperty("CLOUDINARY_API_SECRET", dotenv.get("CLOUDINARY_API_SECRET"));
        System.setProperty("JWT_SIGNATURE_KEY", dotenv.get("JWT_SIGNATURE_KEY"));

        System.setProperty("VNPAY_HASH_SECRET", dotenv.get("VNPAY_HASH_SECRET"));
        System.setProperty("VNPAY_TMN_CODE", dotenv.get("VNPAY_TMN_CODE"));
        System.setProperty("VNPAY_VERSION", dotenv.get("VNPAY_VERSION"));
        System.setProperty("VNPAY_PAYMENT_URL", dotenv.get("VNPAY_PAYMENT_URL"));
        System.setProperty("VNPAY_RETURN_URL", dotenv.get("VNPAY_RETURN_URL"));


        System.setProperty("PAYMENT_SUCCESS_RETURN_URL", dotenv.get("PAYMENT_SUCCESS_RETURN_URL"));
        System.setProperty("PAYMENT_ERROR_RETURN_URL", dotenv.get("PAYMENT_ERROR_RETURN_URL"));

        System.setProperty("SPRING_MAIL_HOST", dotenv.get("SPRING_MAIL_HOST"));
        System.setProperty("SPRING_MAIL_PROTOCOL", dotenv.get("SPRING_MAIL_PROTOCOL"));
        System.setProperty("SPRING_MAIL_USERNAME", dotenv.get("SPRING_MAIL_USERNAME"));
        System.setProperty("SPRING_MAIL_PASSWORD", dotenv.get("SPRING_MAIL_PASSWORD"));

        System.setProperty("SPRING_REDIS_HOST", dotenv.get("SPRING_REDIS_HOST"));
        System.setProperty("SPRING_REDIS_PORT", dotenv.get("SPRING_REDIS_PORT"));

        SpringApplication.run(ChillShakerApplication.class, args);
    }

}
