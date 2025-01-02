package com.ducnt.chillshaker.controller;

import com.ducnt.chillshaker.dto.request.bar.BarUpdateRequest;
import com.ducnt.chillshaker.dto.response.common.ApiResponse;
import com.ducnt.chillshaker.service.implement.BarService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RestController
@RequestMapping("${api.base-url}")
public class BarController {
    BarService barService;

    @GetMapping("/bar/{id}")
    public ApiResponse getBarById(@PathVariable("id")UUID id) {
        var responseData = barService.getBarById(id);
        return ApiResponse
                .builder()
                .message("Data loaded")
                .code(HttpStatus.OK.value())
                .data(responseData)
                .build();
    }

    @PutMapping("/bar/{id}")
    public ApiResponse updateBar(@PathVariable("id") UUID id, @Valid @ModelAttribute BarUpdateRequest request) {
        var responseData = barService.updateBar(id, request);
        return ApiResponse
                .builder()
                .message("Data loaded")
                .code(HttpStatus.OK.value())
                .data(responseData)
                .build();
    }
}
