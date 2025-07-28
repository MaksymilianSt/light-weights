package com.maksymilianst.lightweights.execution.exception;

public class ExecutionAlreadyExistsException extends TrainingExecutionException {
    private static final String DEFAULT_MESSAGE = "Execution already exists";

    public ExecutionAlreadyExistsException() {
        super(DEFAULT_MESSAGE);
    }
    public ExecutionAlreadyExistsException(String message) {
        super(message);
    }
}
