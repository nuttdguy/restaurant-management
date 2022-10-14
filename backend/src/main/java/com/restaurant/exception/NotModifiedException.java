package com.restaurant.exception;


public class NotModifiedException extends RuntimeException {

    public NotModifiedException() {
        super();
    }

    public NotModifiedException(String message) {
        super(message);
    }

    public NotModifiedException(String message, Throwable cause) {
        super(message, cause);
    }

}
