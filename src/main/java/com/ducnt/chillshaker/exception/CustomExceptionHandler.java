package com.ducnt.chillshaker.exception;

import com.ducnt.chillshaker.dto.response.common.ApiResponse;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintViolation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@RestControllerAdvice
@Slf4j
public class CustomExceptionHandler {
    private static final String MIN_ATTRIBUTE = "min";

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
        ErrorResponse errorResponse = ErrorResponse.BAD_REQUEST;

        String enumKey = Objects.requireNonNull(e.getFieldError()).getDefaultMessage();

        Map<String, Object> attributes = null;
        try {
            errorResponse = ErrorResponse.valueOf(enumKey);
            Optional<ObjectError> objectError = e.getBindingResult().getAllErrors().stream().findFirst();
            if(objectError.isPresent()) {
                var constraintViolation = objectError.get().unwrap(ConstraintViolation.class);
                attributes = constraintViolation.getConstraintDescriptor().getAttributes();
            }
        } catch (IllegalArgumentException ex) {

        }


        return ResponseEntity.status(errorResponse.getHttpStatusCode())
                .body(ApiResponse
                        .builder()
                        .message(Objects.nonNull(attributes) ? attributes(errorResponse.getMessage(), attributes)
                                : Objects.requireNonNull(e.getFieldError()).getDefaultMessage())
                        .build());
    }

    private String attributes(String message, Map<String, Object> attributes) {
        String minValue = String.valueOf(attributes.get(MIN_ATTRIBUTE));

        return message.replace("{" + MIN_ATTRIBUTE + "}", minValue);
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
