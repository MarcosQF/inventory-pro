package com.example.inventorypro.exceptions;

public class ConflictException extends RuntimeException {
  public ConflictException(String message) {
    super(message);
  }
}
