package com.maksymilianst.lightweights.execution.exception;

public class ExecutionFinishedException extends TrainingExecutionException {
    private static final String DEFAULT_MESSAGE = "Execution already finished";

    public ExecutionFinishedException() {
        super(DEFAULT_MESSAGE);
    }
    public ExecutionFinishedException(String message) {
        super(message);
    }
}
