package com.ducnt.chillshaker.service.implement;

import com.ducnt.chillshaker.dto.request.authentication.AuthenticationRequest;
import com.ducnt.chillshaker.dto.request.authentication.LogoutRequest;
import com.ducnt.chillshaker.dto.request.authentication.RefreshRequest;
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
    InvalidationTokenRepository invalidationTokenRepository;

    @NonFinal
    @Value("${jwt.jwt-signature-key}")
    protected String JWT_SIGNATURE_KEY;

    @NonFinal
    @Value("${jwt.accessible-duration}")
    protected Long ACCESSIBLE_DURATION;

    @NonFinal
    @Value("${jwt.refreshable-duration}")
    protected Long REFRESHABLE_DURATION;

    @NonFinal
    @Value("${jwt.accessible-duration-type}")
    protected String ACCESSIBLE_DURATION_TYPE;

    @NonFinal
    @Value("${jwt.refreshable-duration-type}")
    protected String REFRESHABLE_DURATION_TYPE;

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        var account = accountRepository.findByEmail(request.getEmail()).orElseThrow(() -> new NotFoundException("Account not found"));

        PasswordEncoder bcrypt = new BCryptPasswordEncoder();
        boolean isAuthenticated = bcrypt.matches(request.getPassword(), account.getPassword());
        if (!isAuthenticated) {
            throw new CustomException(ErrorResponse.UNAUTHENTICATED);
        }
        var accessToken = generateAccessToken(account);
        var refreshToken = generateRefreshToken(account);
        return AuthenticationResponse
                .builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    private String generateAccessToken(Account account) {
        JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .issuer("chillshaker.com")
                .subject(account.getEmail())
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now()
                                .plus(ACCESSIBLE_DURATION, ChronoUnit.valueOf(ACCESSIBLE_DURATION_TYPE))
                                .toEpochMilli()
                ))
                .claim("scope", buildScope(account.getRoles()))
                .build();

        return getTokenString(jwsHeader, jwtClaimsSet);
    }

    private String generateRefreshToken(Account account) {
        JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .issuer("chillshaker.com")
                .subject(account.getEmail())
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now()
                                .plus(REFRESHABLE_DURATION, ChronoUnit.valueOf(REFRESHABLE_DURATION_TYPE))
                                .toEpochMilli()
                ))
                .jwtID(UUID.randomUUID().toString())
                .build();

        return getTokenString(jwsHeader, jwtClaimsSet);
    }

    private String getTokenString(JWSHeader jwsHeader, JWTClaimsSet jwtClaimsSet) {
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
        var signToken = verifyToken(logoutRequest.getRefreshToken());

        var jit = signToken.getJWTClaimsSet().getJWTID();
        var expirationTime = signToken.getJWTClaimsSet().getExpirationTime();

        InvalidationToken invalidationToken = InvalidationToken
                .builder()
                .id(UUID.fromString(jit))
                .expireTime(expirationTime)
                .build();

        invalidationTokenRepository.save(invalidationToken);
    }

    public AuthenticationResponse refreshToken(RefreshRequest request) throws ParseException, JOSEException {

        var signToken = verifyToken(request.getRefreshToken());

        var account = accountRepository.findByEmail(signToken.getJWTClaimsSet().getSubject())
                .orElseThrow(() -> new CustomException(ErrorResponse.UNAUTHENTICATED));

        var accessToken = generateAccessToken(account);
        return AuthenticationResponse
                .builder()
                .accessToken(accessToken)
                .build();
    }

    private SignedJWT verifyToken(String token) throws JOSEException, ParseException {
        JWSVerifier verifier = new MACVerifier(JWT_SIGNATURE_KEY.getBytes());

        SignedJWT signedJWT = SignedJWT.parse(token);

        Date expirationTime = signedJWT.getJWTClaimsSet().getExpirationTime();
        String jti = signedJWT.getJWTClaimsSet().getJWTID();

        if(!(signedJWT.verify(verifier) && expirationTime.after(new Date())))
            throw new CustomException(ErrorResponse.UNAUTHENTICATED);

        if(jti != null && invalidationTokenRepository.existsById(UUID.fromString(jti)))
            throw new CustomException(ErrorResponse.UNAUTHENTICATED);

        return signedJWT;
    }
}
