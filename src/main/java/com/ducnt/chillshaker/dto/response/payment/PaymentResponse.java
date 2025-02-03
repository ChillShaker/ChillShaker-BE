package com.ducnt.chillshaker.dto.response.payment;

import com.ducnt.chillshaker.enums.PaymentStatusEnum;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaymentResponse {
    String providerName;
    String transactionCode;
    LocalDate paymentDate;
    double paymentFee;
    double totalPrice;
    String Note;
    PaymentStatusEnum status;

    String email;
}
