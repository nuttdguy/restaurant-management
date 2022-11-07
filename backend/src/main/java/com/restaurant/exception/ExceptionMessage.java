package com.restaurant.exception;

public class ExceptionMessage {

    public static final String NOT_FOUND = "%s was not found.";
    public static final String EXPIRED_TOKEN = "%s has expired.";
    public static final String NOT_VERIFIED = "%s is not verified - verify your email to activate.";
    public static final String ENTITY_EXISTS = "%s exists - %s";
    public static final String ENTITY_NOT_EXISTS = "%s does not exist or is invalid";
    public static final String BAD_CREDENTIAL = "%s or %s is incorrect or invalid";
    public static final String DELETE_FAILURE = "Failed to delete %s. Record is invalid or does not exist.";
    public static final String VALIDATION_FAILURE = "%s does not satisfy validation requirement - %s %s.";

}
