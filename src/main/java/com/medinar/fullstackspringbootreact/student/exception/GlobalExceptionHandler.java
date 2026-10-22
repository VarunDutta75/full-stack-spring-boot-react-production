package com.medinar.fullstackspringbootreact.student.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.HashMap;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNotFound(
            NotFoundException ex) {

        Map<String, Object> body = Map.of(
                "timestamp", LocalDateTime.now().toString(),
                "status", 404,
                "error", "Not Found",
                "message", ex.getMessage()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Map<String, Object>> handleBadRequest(
            BadRequestException ex) {

        Map<String, Object> body = Map.of(
                "timestamp", LocalDateTime.now().toString(),
                "status", 400,
                "error", "Bad Request",
                "message", ex.getMessage()
        );

        return ResponseEntity.badRequest().body(body);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
public ResponseEntity<Map<String, Object>> handleValidationErrors(
        MethodArgumentNotValidException ex) {

    Map<String, String> errors = new HashMap<>();

    ex.getBindingResult().getFieldErrors().forEach(error ->
            errors.put(error.getField(), error.getDefaultMessage())
    );

    Map<String, Object> body = Map.of(
            "status", 400,
            "error", "Validation Failed",
            "messages", errors
    );

    return ResponseEntity.badRequest().body(body);
}
}