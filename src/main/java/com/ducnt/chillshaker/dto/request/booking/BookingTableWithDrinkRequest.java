package com.ducnt.chillshaker.dto.request.booking;

import com.ducnt.chillshaker.enums.BookingTypeEnum;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookingTableWithDrinkRequest extends GenericBookingRequest {
    List<BookingDrinkRequest> drinks;
    BookingTypeEnum bookingType = BookingTypeEnum.BOOKING_WITH_DRINK;
}
