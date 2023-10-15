package com.mprribeiro.authuser.resources.exceptions;

import com.mprribeiro.authuser.services.exceptions.ArgumentAlreadyTakenException;
import com.mprribeiro.authuser.services.exceptions.UserNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(ArgumentAlreadyTakenException.class)
    public ResponseEntity<StandardError> emailAlreadyTaken(final ArgumentAlreadyTakenException exception,
                                                           final HttpServletRequest request) {
        final var standardError = new StandardError();
        standardError.setTimestamp(LocalDateTime.now());
        standardError.setStatus(HttpStatus.CONFLICT.value());
        standardError.setError("Argument already taken!");
        standardError.setMessage(exception.getMessage());
        standardError.setPath(request.getRequestURI());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(standardError);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<StandardError> userNotFound(final UserNotFoundException exception,
                                                      final HttpServletRequest request) {
        final var standardError = new StandardError();
        standardError.setTimestamp(LocalDateTime.now());
        standardError.setStatus(HttpStatus.NOT_FOUND.value());
        standardError.setError("User not found!");
        standardError.setMessage(exception.getMessage());
        standardError.setPath(request.getRequestURI());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(standardError);
    }
}
