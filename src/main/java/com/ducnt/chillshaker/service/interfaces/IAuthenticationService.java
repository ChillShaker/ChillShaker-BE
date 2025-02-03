package com.ducnt.chillshaker.service.interfaces;

import com.ducnt.chillshaker.dto.request.authentication.*;
import com.ducnt.chillshaker.dto.response.authentication.AuthenticationResponse;
import com.nimbusds.jose.JOSEException;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;

public interface IAuthenticationService {
    AuthenticationResponse authenticate(AuthenticationRequest request);

    boolean introspect(String token) throws JOSEException, ParseException;

    void logout(LogoutRequest logoutRequest) throws JOSEException, ParseException;

    AuthenticationResponse refreshToken(RefreshRequest request) throws ParseException, JOSEException;

    @Transactional
    boolean register(SignUpRequest request);

    @Transactional
    boolean verifyAccountWithOtp(VerifyOtpRequest request);
}
