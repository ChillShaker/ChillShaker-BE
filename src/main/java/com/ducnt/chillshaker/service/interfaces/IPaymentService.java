package com.ducnt.chillshaker.service.interfaces;

import com.ducnt.chillshaker.dto.response.payment.PaymentResponse;

public interface IPaymentService {
    PaymentResponse getPaymentInfo(String transactionNo);
}
