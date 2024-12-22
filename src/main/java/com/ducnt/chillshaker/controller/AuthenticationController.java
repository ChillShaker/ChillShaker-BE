package com.ducnt.chillshaker.controller;

import com.ducnt.chillshaker.dto.request.authentication.AuthenticationRequest;
import com.ducnt.chillshaker.dto.request.authentication.LogoutRequest;
import com.ducnt.chillshaker.dto.response.common.ApiResponse;
import com.ducnt.chillshaker.service.implement.AuthenticationService;
import com.nimbusds.jose.JOSEException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

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

    @PostMapping("/log-out")
    public ApiResponse logout(@RequestBody LogoutRequest request) throws ParseException, JOSEException {
        authenticationService.logout(request);
        return ApiResponse.builder().build();
    }
}
