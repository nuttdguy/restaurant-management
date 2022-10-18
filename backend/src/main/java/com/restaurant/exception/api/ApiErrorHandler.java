package com.restaurant.exception.api;

import com.restaurant.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.JDBCException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Optional.ofNullable;

@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class ApiErrorHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ AuthenticationException.class })
    public ResponseEntity<ApiError<String>> handleAuthenticationException(Exception ex) {

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new ApiError<>("Authentication failed", List.of(ex.getLocalizedMessage())));
    }

    @ExceptionHandler({ AccessDeniedException.class })
    public ResponseEntity<ApiError<String>> handleAccessDeniedException(Exception ex) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new ApiError<>("Access denied", List.of(ex.getLocalizedMessage())));
    }

    @ExceptionHandler(value = {UserNotFoundException.class})
    public ResponseEntity<ApiError<String>> handleNotFoundException(WebRequest request, UserNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ApiError<>("Not found", List.of(ex.getLocalizedMessage())));
    }

    @ExceptionHandler(value = {AccountNotActiveException.class})
    public ResponseEntity<ApiError<String>> handleAccountNoActiveException(WebRequest request, AccountNotActiveException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ApiError<>(ex.getLocalizedMessage(), List.of(ex.getLocalizedMessage())));
    }

    @ExceptionHandler(UserExistsException.class)
    public ResponseEntity<ApiError<String>> handleUserExistsException(WebRequest request, UserExistsException ex) {
        return ResponseEntity
                .status(HttpStatus.FOUND)
                .body(new ApiError<>("User exists", List.of(ex.getLocalizedMessage())));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiError<String>> handleBadCredentialsException(WebRequest request, BadCredentialsException ex) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new ApiError<>("Bad Credentials", List.of(ex.getLocalizedMessage())));
    }

    @ExceptionHandler(TokenNotFoundException.class)
    public ResponseEntity<ApiError<String>> handleTokenNotFoundException(WebRequest request, TokenNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ApiError<>("Token not found", List.of(ex.getLocalizedMessage())));
    }

    @ExceptionHandler({TokenExpiredException.class, TokenExistsException.class})
    public <T extends RuntimeException> ResponseEntity<ApiError<String>> handleTokenExpiredException(
            WebRequest request, T ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiError<>("Token exists or is expired", List.of(ex.getLocalizedMessage())));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiError<Map<String, String>>>
    handleMethodArgumentTypeMismatchException(HttpServletRequest request, MethodArgumentTypeMismatchException ex) {
        log.error("handleMethodArgumentTypeMismatchException {}\n", request.getRequestURI(), ex);

        var details = new HashMap<String, String>();
        details.put("paramName", ex.getName());
        details.put("paramValue", ofNullable(ex.getValue()).map(Object::toString).orElse(""));
        details.put("errorMessage", ex.getMessage());

        return ResponseEntity.badRequest()
                .body(new ApiError<>("Method argument type mismatch", List.of(details)));
    }

    @ExceptionHandler({
            SQLIntegrityConstraintViolationException.class,
            ConstraintViolationException.class,
            DataIntegrityViolationException.class})
    public <T extends JDBCException> ResponseEntity<ApiError<String>> handleSQLIntegrityConstraintException(
            WebRequest request, T ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiError<>("SQL Constraint Violation", List.of(ex.getLocalizedMessage())));
    }

}