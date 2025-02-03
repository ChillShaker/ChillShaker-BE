package com.ducnt.chillshaker.controller;

import com.ducnt.chillshaker.dto.request.authentication.*;
import com.ducnt.chillshaker.dto.response.common.ApiResponse;
import com.ducnt.chillshaker.service.implement.AuthenticationService;
import com.nimbusds.jose.JOSEException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("${api.base-url}")
public class AuthenticationController {
    AuthenticationService authenticationService;

    @PostMapping("/login")
    public ApiResponse login(@RequestBody AuthenticationRequest request) {
        var responseData = authenticationService.authenticate(request);
        return ApiResponse
                .builder()
                .data(responseData)
                .build();
    }

    @PostMapping("/register")
    public ApiResponse signUp(@RequestBody SignUpRequest request) {
        var responseData = authenticationService.register(request);
        return ApiResponse
                .builder()
                .code(responseData ? HttpStatus.OK.value() : HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(responseData ? "OTP has been sent to your email!" : "Internal server error")
                .build();
    }

    @PostMapping("/verify")
    public ApiResponse verifyOtp(@RequestBody VerifyOtpRequest request) {
        var responseData = authenticationService.verifyAccountWithOtp(request);
        return ApiResponse
                .builder()
                .code(responseData ? HttpStatus.OK.value() : HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(responseData ? "Account verified successfully please login" : "Internal server error")
                .build();
    }

    @PostMapping("/logout")
    public ApiResponse logout(@RequestBody LogoutRequest request) throws ParseException, JOSEException {
        authenticationService.logout(request);
        return ApiResponse.builder().build();
    }


    @PostMapping("/refresh")
    public ApiResponse refresh(@RequestBody RefreshRequest request) throws ParseException, JOSEException {
        var responseData = authenticationService.refreshToken(request);
        return ApiResponse
                .builder()
                .data(responseData)
                .build();
    }
}
