package com.ducnt.chillshaker.controller;

import com.ducnt.chillshaker.dto.response.common.ApiResponse;
import com.ducnt.chillshaker.dto.response.vnpay.VNPayResponse;
import com.ducnt.chillshaker.service.thirdparty.VNPayService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api.base-url}")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class PaymentController {
    VNPayService vnPayService;

    @GetMapping("/vnpay-return")
    public ApiResponse processVnpayReturn(@ModelAttribute VNPayResponse vnPayResponse) {
        var dataResponse = vnPayService.processPaymentReturn(vnPayResponse);
        return ApiResponse
                .builder()
                .code(HttpStatus.OK.value())
                .data(dataResponse)
                .message("Payment finish success")
                .build();
    }
}
