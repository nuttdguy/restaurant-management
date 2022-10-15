package com.restaurant.exception;

public class TokenExistsException extends RuntimeException {

    public TokenExistsException() {
        super();
    }

    public TokenExistsException(String message) {
        super(message);
    }

    public TokenExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
