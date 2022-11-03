package com.restaurant.exception;

public class ExceptionMessage {

    public final static String NOT_FOUND = "%s was not found.";
    public final static String EXPIRED_TOKEN = "%s has expired.";
    public final static String NOT_VERIFIED = "%s is not verified - verify your email to activate.";
    public final static String ENTITY_EXISTS = "%s exists - %s";
    public final static String BAD_CREDENTIAL = "%s or %s is incorrect or invalid";
    public final static String DELETE_FAILURE = "Failed to delete %s. Record is invalid or does not exist.";
    public final static String VALIDATION_FAILURE = "%s does not satisfy validation requirement - %s %s.";
}
