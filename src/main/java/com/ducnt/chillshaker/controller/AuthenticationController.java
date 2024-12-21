package com.ducnt.chillshaker.controller;

import com.ducnt.chillshaker.dto.request.AuthenticationRequest;
import com.ducnt.chillshaker.dto.response.common.ApiResponse;
import com.ducnt.chillshaker.service.implement.AuthenticationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/api/v1")
public class AuthenticationController {
    AuthenticationService authenticationService;
    @PostMapping("/log-in")
    public ApiResponse login(@RequestBody AuthenticationRequest request) {
        var responseData = authenticationService.authenticate(request);
        return ApiResponse.builder().data(responseData).build();
    }
}
