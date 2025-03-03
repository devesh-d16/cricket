package com.devesh.cricket.exceptions;

public class DuplicateResourceFoundException extends RuntimeException {
  public DuplicateResourceFoundException(String message) {
    super(message);
  }
}
