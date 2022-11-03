package com.restaurant.exception;

public class NotActiveException extends RuntimeException {

    public NotActiveException() {
        super();
    }

    public NotActiveException(String message) {
        super(message);
    }

    public NotActiveException(String message, Throwable cause) {
        super(message, cause);
    }

}
