package org.example.syntheticcorestarter.exception;

import jakarta.validation.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.RejectedExecutionException;

@RestControllerAdvice
public class ExceptionManager {

    private ResponseEntity<Object> buildErrorResponse(Exception ex, WebRequest request, HttpStatus status, String error) {
        var body = new HashMap<>();
        body.put("timestamp", new Date());
        body.put("status", status.value());
        body.put("error", error);
        body.put("message", ex.getMessage());
        body.put("path", request.getDescription(false).replace("uri=", ""));

        return new ResponseEntity<>(body, status);
    }

    @ExceptionHandler({
            ValidationException.class,
            RejectedExecutionException.class,
            InterruptedException.class,
            Exception.class
    })

    public ResponseEntity<Object> handleExceptions(Exception ex, WebRequest request) {
        HttpStatus status;
        String error;

        switch (ex.getClass().getSimpleName()) {
            case "ValidationException" -> {
                status = HttpStatus.UNPROCESSABLE_ENTITY;
                error = "Validation Error";
            }
            case "RejectedExecutionException" -> {
                status = HttpStatus.TOO_MANY_REQUESTS;
                error = "Too Many Requests";
            }
            case "InterruptedException" -> {
                status = HttpStatus.SERVICE_UNAVAILABLE;
                error = "Service Interrupted";
            }
            default -> {
                status = HttpStatus.INTERNAL_SERVER_ERROR;
                error = "Internal Server Error";
            }
        }

        return buildErrorResponse(ex, request, status, error);
    }
}