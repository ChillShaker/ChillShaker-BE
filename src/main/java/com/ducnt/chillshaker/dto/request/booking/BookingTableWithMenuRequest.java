package com.ducnt.chillshaker.dto.request.booking;

import com.ducnt.chillshaker.enums.BookingTypeEnum;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookingTableWithMenuRequest extends GenericBookingRequest{
    UUID menuId;
    BookingTypeEnum bookingType = BookingTypeEnum.BOOKING_WITH_MENU;
}
