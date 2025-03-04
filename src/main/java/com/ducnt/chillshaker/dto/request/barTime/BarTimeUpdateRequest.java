package com.ducnt.chillshaker.dto.request.barTime;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BarTimeUpdateRequest {
    int dayOfWeek;
    LocalTime startTime;
    LocalTime endTime;
}
