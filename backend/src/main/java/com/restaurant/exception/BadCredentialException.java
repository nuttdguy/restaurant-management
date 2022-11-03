package com.restaurant.exception;

public class BadCredentialException extends RuntimeException {

    public BadCredentialException() {
        super();
    }

    public BadCredentialException(String message) {
        super(message);
    }

    public BadCredentialException(String message, Throwable cause) {
        super(message, cause);
    }


}
