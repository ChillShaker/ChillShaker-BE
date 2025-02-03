package com.ducnt.chillshaker.controller;

import com.ducnt.chillshaker.dto.response.common.ApiResponse;
import com.ducnt.chillshaker.dto.response.payment.PaymentResponse;
import com.ducnt.chillshaker.dto.response.vnpay.VNPayResponse;
import com.ducnt.chillshaker.service.implement.PaymentService;
import com.ducnt.chillshaker.service.thirdparty.VNPayService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("${api.base-url}")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Slf4j
public class PaymentController {
    @Value("${payment.success-url}")
    @NonFinal
    String paymentSuccessUrl;

    @Value("${payment.error-url}")
    @NonFinal
    String paymentErrorUrl;

    VNPayService vnPayService;
    PaymentService paymentService;

    @GetMapping("/vnpay-return")
    public ResponseEntity<Void> processVnpayReturn(@ModelAttribute VNPayResponse vnPayResponse) throws IOException {
        boolean isSuccess = vnPayService.processPaymentReturn(vnPayResponse);
        String redirectUrl;
        if (isSuccess) {
            redirectUrl = String.format(
                    "%s?vnp_ResponseCode=%s&vnp_TransactionNo=%s",
                    paymentSuccessUrl,
                    vnPayResponse.getVnp_ResponseCode(),
                    vnPayResponse.getVnp_TransactionNo()
            );
        } else {
            redirectUrl = String.format(
                    "%s?vnp_ResponseCode=%s",
                    paymentErrorUrl,
                    vnPayResponse.getVnp_ResponseCode()
            );
        }

        return ResponseEntity
                .status(HttpStatus.FOUND)
                .header(HttpHeaders.LOCATION, redirectUrl)
                .build();
    }

    @GetMapping("/payment-status")
    public ApiResponse getPaymentStatus(
            @RequestParam String transactionNo
    ) {
        try {
            PaymentResponse paymentInfo = paymentService.getPaymentInfo(transactionNo);

            if (paymentInfo != null) {
                return ApiResponse
                        .builder()
                        .code(HttpStatus.OK.value())
                        .data(paymentInfo)
                        .build();
            } else {
                return ApiResponse
                        .builder()
                        .code(HttpStatus.NOT_FOUND.value())
                        .data(null)
                        .build();
            }
        } catch (Exception e) {
            log.error("Error getting payment status", e);
            return ApiResponse
                    .builder()
                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .data(null)
                    .build();
        }
    }
}
