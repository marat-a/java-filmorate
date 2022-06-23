package ru.yandex.practicum.filmorate.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;

import java.util.Map;

@ControllerAdvice
public class ErrorHandler {
    @ExceptionHandler
    public ResponseEntity<Map<String, String>> validationError(final ValidationException e) {
        return new ResponseEntity<>(
                Map.of("Error", e.getMessage()),
                HttpStatus.BAD_REQUEST
        );
    }
    @ExceptionHandler
    public ResponseEntity<Map<String, String>> notFoundObject(final NotFoundException e) {
        return new ResponseEntity<>(
                Map.of("Error", e.getMessage()),
                HttpStatus.NOT_FOUND
        );
    }
    @ExceptionHandler
    public ResponseEntity<Map<String, String>> notFoundObject(final RuntimeException e) {
        return new ResponseEntity<>(
                Map.of("Error", e.getMessage()),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}