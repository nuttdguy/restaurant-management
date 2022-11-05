package com.restaurant.exception;

import org.springframework.security.core.AuthenticationException;

public class AuthFilterException extends AuthenticationException  {
    public AuthFilterException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public AuthFilterException(String msg) {
        super(msg);
    }
}
