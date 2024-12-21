package com.ducnt.chillshaker.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public enum ErrorResponse {
    DATA_EXISTED("Data is existed", HttpStatus.BAD_REQUEST),
    BAD_REQUEST("Invalid request", HttpStatus.BAD_REQUEST),
    UNAUTHENTICATED("Unauthenticated", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED("You do not have permission to access this resource", HttpStatus.FORBIDDEN),
    NOT_FOUND("Data not found", HttpStatus.NOT_FOUND),
    ;

    private String message;
    private HttpStatusCode httpStatusCode;
}
