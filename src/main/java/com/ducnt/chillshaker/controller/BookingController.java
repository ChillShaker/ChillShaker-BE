package com.ducnt.chillshaker.controller;

import com.ducnt.chillshaker.dto.request.booking.BookingTableOnlyRequest;
import com.ducnt.chillshaker.dto.request.booking.BookingTableWithDrinkRequest;
import com.ducnt.chillshaker.dto.request.booking.BookingTableWithMenuRequest;
import com.ducnt.chillshaker.dto.response.common.ApiResponse;
import com.ducnt.chillshaker.service.implement.BookingService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("${api.base-url}")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class BookingController {
    BookingService bookingService;

    @PostMapping("/booking-only-table")
    public ApiResponse createBookingTableOnly(HttpServletRequest servletRequest,
                                              @RequestBody BookingTableOnlyRequest request)
    {
        var dataResponse = bookingService.createBookingTableOnly(servletRequest, request);

        return ApiResponse
                .builder()
                .code(HttpStatus.OK.value())
                .data(dataResponse)
                .message("Booking only table success")
                .build();
    }

    @PostMapping("/booking-table-with-drink")
    public ApiResponse createBookingTableWithDrink(HttpServletRequest servletRequest,
                                                   @RequestBody BookingTableWithDrinkRequest request) {
        var dataResponse = bookingService.createBookingTableWithDrink(servletRequest, request);
        return ApiResponse
                .builder()
                .code(HttpStatus.OK.value())
                .data(dataResponse)
                .message("Booking table with drink success")
                .build();
    }

    @PostMapping("/booking-table-with-menu")
    public ApiResponse createBookingTableWithMenu(HttpServletRequest servletRequest,
                                                  @RequestBody BookingTableWithMenuRequest request) {
        var dataResponse = bookingService.createBookingTableWithMenu(servletRequest, request);
        return ApiResponse
                .builder()
                .code(HttpStatus.OK.value())
                .data(dataResponse)
                .message("Booking table with menu success")
                .build();
    }

    @GetMapping("/booking-info/{id}")
    public ApiResponse getBookingInfoById(@PathVariable("id")UUID id) {
        var dataResponse = bookingService.getBookingInfoById(id);
        return ApiResponse
                .builder()
                .code(HttpStatus.OK.value())
                .data(dataResponse)
                .message("Data loaded")
                .build();
    }
}
