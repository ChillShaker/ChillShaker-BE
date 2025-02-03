package com.ducnt.chillshaker.dto.request.barTable;

import com.ducnt.chillshaker.enums.BarTableStatusEnum;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BarTableStatusRequest {
    LocalDate bookingDate;
    LocalTime bookingTime;
    UUID barTableId;
    BarTableStatusEnum status;
    String userEmail;
}
