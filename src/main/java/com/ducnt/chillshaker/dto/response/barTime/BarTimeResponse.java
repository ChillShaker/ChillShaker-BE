package com.ducnt.chillshaker.dto.response.barTime;

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
public class BarTimeResponse {
    int dayOfWeek;
    LocalTime startTime;
    LocalTime endTime;
}
