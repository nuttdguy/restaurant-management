package com.restaurant.exception;

import java.util.List;

public class RequiredFieldException extends RuntimeException {

    List<String> details;

    public RequiredFieldException() { }

    public RequiredFieldException(String message) {
        super(message);
        this.details = List.of(message);
    }

    public RequiredFieldException(String message, List<String> details) {
        super(message);
        this.details = details;
    }

    public RequiredFieldException(String message, Throwable cause) {
        super(message, cause);
    }

    public List<String> getDetails() {
        return details;
    }

}

