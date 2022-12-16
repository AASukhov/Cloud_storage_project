package com.example.diploma.exception;

public class UnauthorizedUserException extends RuntimeException{
    public UnauthorizedUserException(String message) {
        super(message);
    }
}
