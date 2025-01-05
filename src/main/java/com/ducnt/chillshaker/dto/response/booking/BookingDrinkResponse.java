package com.ducnt.chillshaker.dto.response.booking;

import com.ducnt.chillshaker.dto.response.drink.DrinkResponse;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookingDrinkResponse {
    DrinkResponse drink;
    int quantity;
}
