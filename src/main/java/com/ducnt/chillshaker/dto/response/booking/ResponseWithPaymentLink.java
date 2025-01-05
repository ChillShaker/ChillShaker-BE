package com.ducnt.chillshaker.dto.response.booking;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class ResponseWithPaymentLink {
    BookingResponse bookingResponse;
    String paymentLink;
}
