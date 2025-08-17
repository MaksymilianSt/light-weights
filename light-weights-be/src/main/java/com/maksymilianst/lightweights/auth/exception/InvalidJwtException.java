package com.maksymilianst.lightweights.auth.exception;

public class InvalidJwtException extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "Invalid JWT token";

    public InvalidJwtException() {
        this(DEFAULT_MESSAGE);
    }

    public InvalidJwtException(String message) {
        super(message);
    }
}
