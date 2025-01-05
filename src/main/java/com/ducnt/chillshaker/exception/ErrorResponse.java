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
    /** ERROR RESPONSE FOR ACCOUNT **/
    FULLNAME_INVALID("FullName must be at least {min} character ", HttpStatus.BAD_REQUEST),
    PASSWORD_INVALID("Password must be at least {min} character ", HttpStatus.BAD_REQUEST),
    DOB_INVALID("Date of birth muse be at least {min}", HttpStatus.BAD_REQUEST),
    EMAIL_INVALID("Email must be a well-formed email address",  HttpStatus.BAD_REQUEST),
    PHONE_INVALID("Phone number is not in Vietnamese syntax", HttpStatus.BAD_REQUEST),

    /** ERROR RESPONSE FOR **/
    FILE_OVER_SIZE("Max file size is {min} MB", HttpStatus.BAD_REQUEST),
    IMAGE_FILE_INVALID("Only jpg, png, gif, bmp files are allowed or not empty file", HttpStatus.BAD_REQUEST),

    /** COMMON ERROR RESPONSE **/
    DATA_EXISTED("Data is existed", HttpStatus.BAD_REQUEST),
    BAD_REQUEST("Invalid request", HttpStatus.BAD_REQUEST),
    UNAUTHENTICATED("Unauthenticated", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED("You do not have permission to access this resource", HttpStatus.FORBIDDEN),
    NOT_FOUND("Data not found", HttpStatus.NOT_FOUND),
    INTERNAL_SERVER("Unspecified error at server", HttpStatus.INTERNAL_SERVER_ERROR),

    PAYMENT_INVALID("Payment method or data is not correct", HttpStatus.BAD_REQUEST),
    UNKNOWN_ERROR_IN_PAYMENT("Payment has an unknown problem", HttpStatus.INTERNAL_SERVER_ERROR);
    ;

    private String message;
    private HttpStatusCode httpStatusCode;
}
