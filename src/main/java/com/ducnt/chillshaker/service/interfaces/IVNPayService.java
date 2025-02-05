package com.ducnt.chillshaker.service.interfaces;

import com.ducnt.chillshaker.dto.response.vnpay.VNPayResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public interface IVNPayService {
    @Transactional
    String getVnpayPaymentLink(UUID bookingId, UUID accountId, double totalPrice,
                               HttpServletRequest servletRequest);

    @Transactional
    boolean processPaymentReturn(VNPayResponse vnPayResponse);
}
