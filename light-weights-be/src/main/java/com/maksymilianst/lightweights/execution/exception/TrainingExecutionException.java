package com.maksymilianst.lightweights.execution.exception;

public class TrainingExecutionException extends RuntimeException {
    private static final String BASE_MESSAGE = "Training execution exception: ";

    public TrainingExecutionException() {
        super(BASE_MESSAGE);
    }
    public TrainingExecutionException(String message) {
        super(BASE_MESSAGE + message);
    }
}
