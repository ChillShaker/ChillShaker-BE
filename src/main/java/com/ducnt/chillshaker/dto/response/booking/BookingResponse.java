package com.ducnt.chillshaker.dto.response.booking;

import com.ducnt.chillshaker.dto.response.payment.PaymentResponse;
import com.ducnt.chillshaker.enums.BookingStatusEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.MappedSuperclass;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
@MappedSuperclass
public class BookingResponse {
    UUID id;
    String bookingCode;
    LocalDate bookingDate;
    LocalTime bookingTime;
    String note;
    double totalPrice;
    UUID checkInStaffId;
    UUID checkOutStaffId;
    LocalDateTime expireAt;
    BookingStatusEnum status;

    List<BookingTableResponse> bookingTables;
    List<BookingDrinkResponse> bookingDrinks;
    List<BookingMenuResponse> bookingMenus;
    PaymentResponse payment;
}
