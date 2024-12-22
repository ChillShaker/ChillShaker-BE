package com.ducnt.chillshaker.service.implement;

import com.ducnt.chillshaker.dto.request.authentication.AuthenticationRequest;
import com.ducnt.chillshaker.dto.request.authentication.LogoutRequest;
import com.ducnt.chillshaker.dto.response.authentication.AuthenticationResponse;
import com.ducnt.chillshaker.exception.CustomException;
import com.ducnt.chillshaker.exception.ErrorResponse;
import com.ducnt.chillshaker.exception.NotFoundException;
import com.ducnt.chillshaker.model.Account;
import com.ducnt.chillshaker.model.InvalidationToken;
import com.ducnt.chillshaker.model.Role;
import com.ducnt.chillshaker.repository.AccountRepository;
import com.ducnt.chillshaker.repository.InvalidationTokenRepository;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Date;
import java.util.StringJoiner;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationService {
    AccountRepository accountRepository;
    InvalidationTokenRepository tokenRepository;
    private final InvalidationTokenRepository invalidationTokenRepository;

    @NonFinal
    @Value("${JWT_SIGNATURE_KEY}")
    protected String JWT_SIGNATURE_KEY;

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        var account = accountRepository.findByEmail(request.getEmail()).orElseThrow(() -> new NotFoundException("Account not found"));

        PasswordEncoder bcrypt = new BCryptPasswordEncoder();
        boolean isAuthenticated = bcrypt.matches(request.getPassword(), account.getPassword());
        if (!isAuthenticated) {
            throw new CustomException(ErrorResponse.UNAUTHENTICATED);
        }
        var accessToken = generateToken(account);
        return AuthenticationResponse.builder().accessToken(accessToken).build();
    }

    private String generateToken(Account account) {
        JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .issuer("chillshaker.com")
                .subject(account.getEmail())
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()
                ))
                .jwtID(UUID.randomUUID().toString())
                .claim("scope", buildScope(account.getRoles()))
                .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(jwsHeader, payload);

        try {
            jwsObject.sign(new MACSigner(JWT_SIGNATURE_KEY.getBytes()));
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }
        return jwsObject.serialize();
    }

    private String buildScope(Collection<Role> roles) {
        StringJoiner stringJoiner = new StringJoiner(" ");
        roles.forEach(role -> {
            stringJoiner.add(role.getName());
        });
        return stringJoiner.toString();
    }

    public boolean introspect (String token) throws JOSEException, ParseException {
        try {
            verifyToken(token);
            return true;
        } catch(Exception ex) {
            return false;
        }

    }

    public void logout(LogoutRequest logoutRequest) throws JOSEException, ParseException {
        var signToken = verifyToken(logoutRequest.getToken());

        var jit = signToken.getJWTClaimsSet().getJWTID();
        var expireDate = signToken.getJWTClaimsSet().getExpirationTime();

        InvalidationToken invalidationToken = InvalidationToken
                .builder()
                .id(UUID.fromString(jit))
                .expireTime(expireDate)
                .build();

        tokenRepository.save(invalidationToken);
    }

    private SignedJWT verifyToken(String token) throws JOSEException, ParseException {
        JWSVerifier verifier = new MACVerifier(JWT_SIGNATURE_KEY.getBytes());

        SignedJWT signedJWT = SignedJWT.parse(token);

        Date expireDate = signedJWT.getJWTClaimsSet().getExpirationTime();

        if(!(signedJWT.verify(verifier) && expireDate.after(new Date())))
            throw new CustomException(ErrorResponse.UNAUTHENTICATED);

        if(invalidationTokenRepository.existsById(UUID.fromString(signedJWT.getJWTClaimsSet().getJWTID())))
            throw new CustomException(ErrorResponse.UNAUTHENTICATED);

        return signedJWT;
    }
}
