package com.application.food_shop.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException e) {
        return buildResponse(e.getMessage(), HttpStatus.NOT_FOUND, "Not Found", null);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationException(MethodArgumentNotValidException e) {
        String message = "Data Validation Failed";

        if (e.getMessage().contains("password")) {
            message = "Incorrect Password. Ensure you have at least 8 digits and one capital letter (A-Z) mixed with lowercase letters, numbers, and symbols";
        }

        Map<String, String> fieldErrors = new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach((fieldError) -> {
            String fieldName = ((FieldError) fieldError).getField();
            String errorMsg = fieldError.getDefaultMessage();
        });
        return buildResponse(message, HttpStatus.BAD_REQUEST, "Validation Error", fieldErrors);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        String message = "Data Integrity Error. Found existing record containing the entered data.";
        String rootCause =  e.getRootCause() != null ? e.getRootCause().getMessage() : "";

        if (rootCause.contains("userinfo_email_key")) {
            message = "Email already exists";
        } else if (rootCause.contains("userinfo_username_key")) {
            message = "Username already exists";
        }

        return buildResponse(message, HttpStatus.CONFLICT, "Data Integrity Violation", null);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Object> handleBadCredentialsException(BadCredentialsException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }

    private ResponseEntity<Object> buildResponse(String message, HttpStatus status, String errorTitle, Object details) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", status.value());
        body.put("error", errorTitle);
        body.put("message", message);

        if(details != null){
            body.put("errors", details);
        }

        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

}
