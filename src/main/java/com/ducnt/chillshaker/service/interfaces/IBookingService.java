package com.ducnt.chillshaker.service.interfaces;

import com.ducnt.chillshaker.dto.request.booking.BookingTableOnlyRequest;
import com.ducnt.chillshaker.dto.request.booking.BookingTableWithDrinkRequest;
import com.ducnt.chillshaker.dto.request.booking.BookingTableWithMenuRequest;
import com.ducnt.chillshaker.dto.response.booking.BookingResponse;
import com.ducnt.chillshaker.dto.response.booking.ResponseWithPaymentLink;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public interface IBookingService {
    @Transactional
    ResponseWithPaymentLink createBookingTableOnly(HttpServletRequest servletRequest,
                                                   BookingTableOnlyRequest request);

    @Transactional
    ResponseWithPaymentLink createBookingTableWithDrink(HttpServletRequest servletRequest,
                                                        BookingTableWithDrinkRequest request);

    @Transactional
    ResponseWithPaymentLink createBookingTableWithMenu(HttpServletRequest servletRequest,
                                                       BookingTableWithMenuRequest request);

    BookingResponse getBookingInfoById(UUID id);
}
