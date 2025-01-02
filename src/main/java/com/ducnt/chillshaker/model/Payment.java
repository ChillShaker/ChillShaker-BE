package com.ducnt.chillshaker.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
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
    int status;


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "account_id")
    Account account;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "booking_id")
    Booking booking;
}
