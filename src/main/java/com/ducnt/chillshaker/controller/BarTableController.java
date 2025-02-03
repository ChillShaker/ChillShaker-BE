package com.ducnt.chillshaker.controller;

import com.cloudinary.Api;
import com.ducnt.chillshaker.dto.request.barTable.BarTableCreationRequest;
import com.ducnt.chillshaker.dto.request.barTable.BarTableStatusRequest;
import com.ducnt.chillshaker.dto.request.barTable.BarTableUpdateRequest;
import com.ducnt.chillshaker.dto.response.barTable.BarTableStatusResponse;
import com.ducnt.chillshaker.dto.response.common.ApiResponse;
import com.ducnt.chillshaker.service.implement.BarTableService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@RestController
@RequestMapping("${api.base-url}")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BarTableController {
    BarTableService barTableService;
    SimpMessagingTemplate simpMessagingTemplate;

    @PostMapping("/bar-table")
    public ApiResponse createBarTable(@RequestBody BarTableCreationRequest request) {
        var dataResponse = barTableService.createBarTable(request);

        return ApiResponse
                .builder()
                .code(HttpStatus.CREATED.value())
                .message("Created bar table success")
                .data(dataResponse)
                .build();
    }

    @PutMapping("/bar-table/{id}")
    public ApiResponse updateBarTable(@PathVariable("id") UUID id, @RequestBody BarTableUpdateRequest request) {
        var dataResponse = barTableService.updateBarTable(id, request);

        return ApiResponse
                .builder()
                .code(HttpStatus.OK.value())
                .message("Updated bar table success")
                .data(dataResponse)
                .build();
    }

    @DeleteMapping("/bar-table/{id}")
    public ApiResponse deletedBarTable(@PathVariable("id") UUID id){
        var dataResponse = barTableService.deleteBarTableById(id);

        return ApiResponse
                .builder()
                .code(HttpStatus.OK.value())
                .message("Deleted bar table success")
                .data(dataResponse)
                .build();
    }

    @GetMapping("/bar-tables")
    public ApiResponse getAllBarTable(
            @RequestParam(required = false, defaultValue = "") String q,
            @RequestParam(required = false, defaultValue = "") String includeProperties,
            @RequestParam(required = false, defaultValue = "name") String attribute,
            @RequestParam(required = false, defaultValue = "") String sort,
            @RequestParam(required = false, defaultValue = "0") Integer pageIndex,
            @RequestParam(required = false, defaultValue = "10") Integer pageSize
    ) {
        var dataResponse = barTableService.getAllBarTableForCustomer(q, includeProperties, attribute, sort, pageIndex, pageSize);

        return ApiResponse
                .builder()
                .code(HttpStatus.OK.value())
                .message("Data loaded success")
                .data(dataResponse)
                .build();
    }

    @GetMapping("/bar-tables/date-time")
    public ApiResponse getAllBarTablesByDateTime(@RequestParam("booking-date") LocalDate bookingDate,
                                                 @RequestParam("booking-time") LocalTime bookingTime) {
        var dataResponse = barTableService.getBarTableByDateTime(bookingDate, bookingTime);
        return ApiResponse
                .builder()
                .code(HttpStatus.OK.value())
                .message("Data loaded success")
                .data(dataResponse)
                .build();
    }

    @GetMapping("/bar-tables-management")
    public ApiResponse getAllBarTableForAdmin(
            @RequestParam(required = false, defaultValue = "") String q,
            @RequestParam(required = false, defaultValue = "") String includeProperties,
            @RequestParam(required = false, defaultValue = "name") String attribute,
            @RequestParam(required = false, defaultValue = "") String sort,
            @RequestParam(required = false, defaultValue = "0") Integer pageIndex,
            @RequestParam(required = false, defaultValue = "10") Integer pageSize
    ) {
        var dataResponse = barTableService.getAllBarTableForAdmin(q, includeProperties, attribute, sort, pageIndex, pageSize);

        return ApiResponse
                .builder()
                .code(HttpStatus.OK.value())
                .message("Data loaded success")
                .data(dataResponse)
                .build();
    }

    @GetMapping("/bar-table/{id}")
    public ApiResponse getBarTableById(@PathVariable("id") UUID id) {
        var dataResponse = barTableService.getBarTableById(id);

        return ApiResponse
                .builder()
                .code(HttpStatus.OK.value())
                .message("Data loaded success")
                .data(dataResponse)
                .build();
    }

    @MessageMapping("/bar-table/status-view")
    @SendTo("/topic/bar-tables")
    public ApiResponse getReservationBarTable(@Payload BarTableStatusRequest request) {
        var dataResponse = barTableService.getStatusBarTable(request);
        return ApiResponse
                .builder()
                .code(HttpStatus.OK.value())
                .message("Data loaded success")
                .data(dataResponse)
                .build();
    }

    @MessageMapping("/bar-table/status-update")
    @SendTo("/topic/bar-tables")
    public ApiResponse setReservationBarTable(@Payload BarTableStatusRequest request) {
        BarTableStatusResponse dataResponse = barTableService.setStatusBarTable(request);
        return ApiResponse
                .builder()
                .data(dataResponse)
                .code(HttpStatus.OK.value())
                .message("Bar table status is updated success")
                .build();
    }
}
