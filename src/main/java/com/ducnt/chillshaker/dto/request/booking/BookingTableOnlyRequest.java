package com.ducnt.chillshaker.dto.request.booking;

import com.ducnt.chillshaker.enums.BookingTypeEnum;
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
public class BookingTableOnlyRequest extends GenericBookingRequest {
    BookingTypeEnum bookingType = BookingTypeEnum.BOOKING_TABLE_ONLY;
}
