package com.devesh.cricket.exceptions;

public class InningNotFoundException extends RuntimeException {
    public InningNotFoundException(String message){
        super(message);
    }
}
