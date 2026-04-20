package com.wallets.api.base.web;

import com.wallets.api.base.exceptions.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
@Slf4j
public class ApiResponseExceptionHandler {

    @ExceptionHandler(value = {ApiException.class})
    protected ResponseEntity<?> handleApiEx(ApiException ex, WebRequest request) {
        if (ex instanceof BadRequestException) {
            return responseEntity(HttpStatus.BAD_REQUEST, ex);
        } else if (ex instanceof NotAuthorizedException) {
            return responseEntity(HttpStatus.UNAUTHORIZED, ex);
        } else if (ex instanceof NotFoundException) {
            return responseEntity(HttpStatus.NOT_FOUND, ex);
        } else if (ex instanceof AlreadyExistsException) {
            return responseEntity(HttpStatus.CONFLICT, ex);
        } else if (ex instanceof NotAcceptableException) {
            return responseEntity(HttpStatus.NOT_ACCEPTABLE, ex);
        } else if (ex instanceof ForbiddenException) {
            return responseEntity(HttpStatus.FORBIDDEN, ex);
        } else if (ex instanceof ServiceUnavailableException) {
            return responseEntity(HttpStatus.SERVICE_UNAVAILABLE, ex);
        }
        return responseEntity(HttpStatus.BAD_REQUEST, ex);
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    protected ResponseEntity<?> handleMethodArgumentNotValidEx(MethodArgumentNotValidException ex, WebRequest request) {
        List<ApiResponse.FieldError> fieldErrors = ex.getBindingResult().getAllErrors().stream()
                .map(error -> {
                    String fieldName = ((FieldError) error).getField();
                    String errorMsg = error.getDefaultMessage();
                    return new ApiResponse.FieldError(fieldName, errorMsg);
                })
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.fieldsError(fieldErrors));
    }

    @ExceptionHandler(value = {Exception.class})
    protected ResponseEntity<?> handleException(Exception ex, WebRequest request) {
        log.debug("Error with web request: {}", request);
        log.debug("Error: ", ex);
        return responseEntity(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error");
    }

    private ResponseEntity<?> responseEntity(HttpStatus status, Throwable ex) {
        return ResponseEntity.status(status).body(ApiResponse.error(status, ex));
    }

    private ResponseEntity<?> responseEntity(HttpStatus status, String msg) {
        return ResponseEntity.status(status).body(ApiResponse.error(status, msg));
    }
}
