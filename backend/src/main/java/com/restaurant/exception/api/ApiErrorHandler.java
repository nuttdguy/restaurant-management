package com.restaurant.exception.api;

import com.restaurant.exception.*;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.JDBCException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.transaction.UnexpectedRollbackException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;
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

    // START :: HANDLER FOR SECURITY / FILTER CHAIN  ::
    @ExceptionHandler({ AuthenticationException.class, ExpiredJwtException.class })
    public ResponseEntity<ApiError<String>> handleAuthenticationException(Exception ex) {

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new ApiError<>(ex.getLocalizedMessage()));
    }

    @ExceptionHandler({ AccessDeniedException.class })
    public ResponseEntity<ApiError<String>> handleAccessDeniedException(Exception ex) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new ApiError<>(ex.getLocalizedMessage()));
    }


    // START :: RUNTIME EXCEPTIONS FOR API AND METHODS
    @ExceptionHandler(FileFormatException.class)
    public ResponseEntity<ApiError<String>> handleFileFormatException (WebRequest request, FileFormatException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ApiError<>(ex.getLocalizedMessage()));
    }


    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiError<String>> handleNotFoundException(WebRequest request, UserNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ApiError<>(ex.getLocalizedMessage()));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiError<String>> handleEntityFoundException(WebRequest request, EntityNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ApiError<>(ex.getLocalizedMessage()));
    }

    @ExceptionHandler(NotActiveException.class)
    public ResponseEntity<ApiError<String>> handleAccountNoActiveException(WebRequest request, NotActiveException ex) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ApiError<>(ex.getLocalizedMessage()));
    }

    @ExceptionHandler(UserExistsException.class)
    public ResponseEntity<ApiError<String>> handleUserExistsException(WebRequest request, UserExistsException ex) {
        return ResponseEntity
                .status(HttpStatus.FOUND)
                .body(new ApiError<>(ex.getLocalizedMessage()));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiError<String>> handleBadCredentialsException(WebRequest request, BadCredentialsException ex) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new ApiError<>(ex.getLocalizedMessage()));
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiError<String>> handleTokenNotFoundException(WebRequest request, NotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ApiError<>(ex.getLocalizedMessage()));
    }

    @ExceptionHandler({ExpiredException.class})
    public ResponseEntity<ApiError<String>> handleExpiredException(WebRequest request, ExpiredException ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiError<>("Expired and / or invalid token", List.of(ex.getLocalizedMessage())));
    }

    @ExceptionHandler({IllegalStateException.class})
    public ResponseEntity<ApiError<String>> handleIllegalStateException(WebRequest request, IllegalStateException ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiError<>(ex.getLocalizedMessage()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiError<String>> handleIllegalArgumentException(WebRequest request, IllegalArgumentException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ApiError<>(ex.getLocalizedMessage()));
    }


    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    public ResponseEntity<ApiError<Map<String, String>>>
    handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex,
                                              HttpServletRequest request) {
        log.error("handleMethodArgumentTypeMismatchException {}\n", request.getRequestURI(), ex);

        var details = new HashMap<String, String>();
        details.put("paramName", ex.getName());
        details.put("paramValue", ofNullable(ex.getValue()).map(Object::toString).orElse(""));
        details.put("errorMessage", ex.getMessage());

        return ResponseEntity.badRequest()
                .body(new ApiError<>("Method argument type mismatch", List.of(details)));
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<ApiError<Map<String, String>>>
    handleIncorrectResultSizeDataAccessException(HttpServletRequest request, IncorrectResultSizeDataAccessException ex) {
        log.error("handleIncorrectResultSizeDataAccessException {}\n", request.getRequestURI(), ex);

        var details = new HashMap<String, String>();
        details.put("paramName", ex.getMessage());
        details.put("paramValue", ofNullable(ex.getMessage()).map(Object::toString).orElse(""));
        details.put("errorMessage", ex.getMessage());

        return ResponseEntity.badRequest()
                .body(new ApiError<>("Method argument type mismatch", List.of(details)));
    }

    @ExceptionHandler({
            UnexpectedRollbackException.class,
            SQLIntegrityConstraintViolationException.class,
            ConstraintViolationException.class,
            DataIntegrityViolationException.class})
    public <T extends JDBCException> ResponseEntity<ApiError<String>> handleSQLIntegrityConstraintException(
            WebRequest request, T ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiError<>("SQL Constraint Violation", List.of(ex.getLocalizedMessage())));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError<String>> handleIllegalArgumentException(WebRequest request, Exception ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiError<>("Internal Server Error", List.of(ex.getLocalizedMessage())));
    }
}