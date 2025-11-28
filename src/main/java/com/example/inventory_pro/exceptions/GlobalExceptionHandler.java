package com.example.inventory_pro.exceptions;

import java.util.HashMap;
import java.util.Map;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<Map<String, String>> handleNotFound(NotFoundException ex) {
    return ResponseEntity
        .status(HttpStatus.NOT_FOUND)
        .body(Map.of("detail", ex.getMessage()));
  }

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<Map<String, Object>> handleConstraintViolation(ConstraintViolationException ex) {

    Map<String, Object> errors = new HashMap<>();
    Map<String, String> fields = new HashMap<>();

    for (ConstraintViolation<?> v : ex.getConstraintViolations()) {
      fields.merge(
          v.getPropertyPath().toString(),
          v.getMessage(),
          (oldVal, newVal) -> oldVal + "; " + newVal);
    }

    errors.put("detail", "Validation failed");
    errors.put("fields", fields);

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String, Object>> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {

    Map<String, Object> errors = new HashMap<>();
    Map<String, String> fields = new HashMap<>();

    ex.getBindingResult().getFieldErrors().forEach(fieldError -> {
      fields.merge(
          fieldError.getField(),
          fieldError.getDefaultMessage(),
          (oldVal, newVal) -> oldVal + "; " + newVal);
    });

    errors.put("detail", "Validation failed");
    errors.put("fields", fields);

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
  }

  @ExceptionHandler(ConflictException.class)
  public ResponseEntity<Map<String, String>> handleConflict(ConflictException ex) {
    return ResponseEntity
        .status(HttpStatus.CONFLICT)
        .body(Map.of("detail", ex.getMessage()));
  }

}
