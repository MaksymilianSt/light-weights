package com.maksymilianst.lightweights.execution.advice;

import com.maksymilianst.lightweights.execution.exception.ExecutionAlreadyExistsException;
import com.maksymilianst.lightweights.execution.exception.ExecutionFinishedException;
import com.maksymilianst.lightweights.execution.exception.TrainingExecutionException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExecutionExceptionHandler {

    public static final String BASE_MSG = "Training execution failed: ";

    @ExceptionHandler(ExecutionFinishedException.class)
    public ResponseEntity<?> handleExecutionFinishedException(ExecutionFinishedException ex) {
        log.warn("Attempted to operate on a finished execution", ex);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(BASE_MSG + ex.getMessage());
    }

    @ExceptionHandler(ExecutionAlreadyExistsException.class)
    public ResponseEntity<?> handleExecutionAlreadyExistsException(ExecutionAlreadyExistsException ex) {
        log.warn("Attempted to create more than one execution from the same training", ex);
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(BASE_MSG + ex.getMessage());
    }

    @ExceptionHandler(TrainingExecutionException.class)
    public ResponseEntity<?> handleTrainingExecutionException(TrainingExecutionException ex) {
        log.error("Training execution error", ex);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(BASE_MSG + ex.getMessage());
    }
}
