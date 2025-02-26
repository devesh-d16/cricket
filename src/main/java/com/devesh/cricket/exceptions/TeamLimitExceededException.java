package com.devesh.cricket.exceptions;

public class TeamLimitExceededException extends RuntimeException {
    public TeamLimitExceededException(String message) {
        super(message);
    }
}
