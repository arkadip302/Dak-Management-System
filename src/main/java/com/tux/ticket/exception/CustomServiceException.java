package com.tux.ticket.exception;

public class CustomServiceException extends RuntimeException {
    public CustomServiceException(String message) {
        super(message);
    }
}
