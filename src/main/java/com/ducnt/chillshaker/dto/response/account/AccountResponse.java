package com.ducnt.chillshaker.dto.response.account;

import com.ducnt.chillshaker.dto.response.booking.BookingResponse;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountResponse {
    UUID id;
    String fullName;
    String email;
    String phone;
    String address;
    String image;
    LocalDate dob;

    List<BookingResponse> bookings;
}
