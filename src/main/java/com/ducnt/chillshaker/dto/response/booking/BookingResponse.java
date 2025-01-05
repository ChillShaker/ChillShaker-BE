package com.ducnt.chillshaker.dto.response.booking;

import com.fasterxml.jackson.annotation.JsonInclude;
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
public class BookingResponse {
    String bookingCode;
    LocalDate bookingDate;
    LocalTime bookingTime;
    String note;
    double totalPrice;
    UUID checkInStaffId;
    UUID checkOutStaffId;
    int numberOfPeople;
    LocalDateTime expireAt;

    List<BookingTableResponse> bookingTables;
    List<BookingDrinkResponse> bookingDrinks;
    List<BookingMenuResponse> bookingMenus;
}
