package com.maksymilianst.lightweights.auth.exception;

import java.util.List;
import java.util.Map;

public class RegistrationValidationException extends RuntimeException {
    private static final String DEFAULT_ERROR_MESSAGE = "Validation error during registration";

    private final Map<String, List<String>> errors;

    public RegistrationValidationException(Map<String, List<String>> errors) {
        super(DEFAULT_ERROR_MESSAGE);
        this.errors = errors;
    }

    public Map<String, List<String>> getErrors() {
        return errors;
    }
}
