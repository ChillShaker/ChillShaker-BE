package com.ducnt.chillshaker.model;

import com.ducnt.chillshaker.enums.PaymentStatusEnum;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class Payment extends BaseModel {
    String providerName;
    String transactionCode;
    LocalDate paymentDate;
    double paymentFee;
    double totalPrice;
    String Note;
    PaymentStatusEnum status;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    Account account;

    @OneToOne(fetch = FetchType.LAZY)
    Booking booking;
}
