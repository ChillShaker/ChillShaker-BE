package com.ducnt.chillshaker.util;

import com.ducnt.chillshaker.exception.CustomException;
import com.ducnt.chillshaker.exception.ErrorResponse;
import com.ducnt.chillshaker.model.BarTime;
import lombok.experimental.UtilityClass;

import java.time.*;
import java.util.Collection;
import java.util.Optional;

@UtilityClass
public class TimeValidatorUtil {
    public Optional<Boolean> validateBookingDateTime(LocalDate bookingDate, LocalTime bookingTime,
                                                     Collection<BarTime> barTimes) {
        ZonedDateTime zonedDateTime = LocalDateTime.now().atZone(ZoneId.systemDefault());
        ZonedDateTime requestDateTime = LocalDateTime.of(bookingDate, bookingTime)
                .atZone(ZoneId.systemDefault());

        if(zonedDateTime.isAfter(requestDateTime)) {
            throw new CustomException(ErrorResponse.TIME_INVALID);
        }

        boolean b = barTimes.stream().anyMatch(barTime ->
                (barTime.getEndTime().isAfter(bookingTime) || barTime.getEndTime().equals(bookingTime))
                && (barTime.getStartTime().isBefore(bookingTime) || barTime.getStartTime().equals(bookingTime))
                && barTime.getDayOfWeek() == bookingDate.getDayOfWeek().getValue()
        );
        return Optional.of(b);
    }
}
