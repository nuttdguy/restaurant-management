package com.restaurant.exception;


public class NotUpdatedException extends RuntimeException {

    public NotUpdatedException() {
        super();
    }

    public NotUpdatedException(String message) {
        super(message);
    }

    public NotUpdatedException(String message, Throwable cause) {
        super(message, cause);
    }

}
