package com.ducnt.chillshaker.exception;

import com.ducnt.chillshaker.dto.response.common.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiResponse> handleNotFoundException(NotFoundException e) {
        ErrorResponse notFound = ErrorResponse.NOT_FOUND;

        return ResponseEntity.status(notFound.getHttpStatusCode())
                .body(ApiResponse.builder().message(e.getMessage()).build());
    }

    @ExceptionHandler(ExistDataException.class)
    public ResponseEntity<ApiResponse> handleExistDataException(ExistDataException e) {
        ErrorResponse dataExisted = ErrorResponse.DATA_EXISTED;

        return ResponseEntity.status(dataExisted.getHttpStatusCode())
                .body(ApiResponse.builder().message(e.getMessage()).build());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse> handleMethodArgumentConversionNotSupportedException(MethodArgumentNotValidException e) {
        ErrorResponse badRequest = ErrorResponse.BAD_REQUEST;

        return ResponseEntity.status(badRequest.getHttpStatusCode())
                .body(ApiResponse.builder().message(Objects.requireNonNull(e.getFieldError()).getDefaultMessage()).build());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponse> handleAccessDeniedException(AccessDeniedException e) {
        ErrorResponse unauthorized = ErrorResponse.UNAUTHORIZED;

        return ResponseEntity.status(unauthorized.getHttpStatusCode())
                .body(ApiResponse.builder().message(unauthorized.getMessage()).build());
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ApiResponse> handleCustomException(CustomException e) {
        ErrorResponse errorResponse = e.getErrorResponse();
        var response = ResponseEntity.status(errorResponse.getHttpStatusCode());
        return response.body(ApiResponse.builder().message(errorResponse.getMessage()).build());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> handleException(CustomException e) {
        var response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR);
        return response.body(ApiResponse.builder().message("Internal server error!").build());
    }
}
