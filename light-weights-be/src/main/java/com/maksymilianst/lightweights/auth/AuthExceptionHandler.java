package com.maksymilianst.lightweights.auth;

import com.maksymilianst.lightweights.auth.dto.ValidationErrorResponse;
import com.maksymilianst.lightweights.auth.exception.RegistrationValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.validation.FieldError;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class AuthExceptionHandler {
    private static final String DEFAULT_AUTH_ERROR_MESSAGE = "Authentication failed";
    private static final String BAD_CREDENTIALS_ERROR_MESSAGE = "Invalid email or password";
    private static final String DEFAULT_VALIDATION_ERROR_MESSAGE = "Validation failed";


    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<?> handleAuthenticationException(AuthenticationException ex) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(DEFAULT_AUTH_ERROR_MESSAGE);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<?> handleBadCredentials(BadCredentialsException ex) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(BAD_CREDENTIALS_ERROR_MESSAGE);
    }

    @ExceptionHandler(RegistrationValidationException.class)
    public ResponseEntity<ValidationErrorResponse> handleRegistrationValidation(RegistrationValidationException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ValidationErrorResponse(ex.getMessage(), ex.getErrors()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, List<String>> validationErrors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.groupingBy(
                        FieldError::getField,
                        Collectors.mapping(FieldError::getDefaultMessage, Collectors.toList())
                ));

        return ResponseEntity.badRequest().body(
                new ValidationErrorResponse(DEFAULT_VALIDATION_ERROR_MESSAGE, validationErrors)
        );
    }
}
