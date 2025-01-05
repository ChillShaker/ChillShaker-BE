package com.ducnt.chillshaker;

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

        SpringApplication.run(ChillShakerApplication.class, args);
    }

}
