package com.ducnt.chillshaker.dto.request.booking;

import jakarta.persistence.MappedSuperclass;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@MappedSuperclass
public class GenericBookingRequest {
    LocalDate bookingDate;
    LocalTime bookingTime;
    String note;
    double totalPrice;
    int numberOfPeople;
    List<UUID> tableIds;
}
