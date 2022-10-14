package com.restaurant.exception;

import javax.persistence.PersistenceException;

public class UserNotFoundException extends PersistenceException {

    public UserNotFoundException() {
        super();
    }

    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
