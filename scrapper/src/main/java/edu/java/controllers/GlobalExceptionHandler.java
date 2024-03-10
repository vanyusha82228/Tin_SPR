package edu.java.controllers;

import edu.java.dto.responseDto.ApiErrorResponse;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiErrorResponse> handleConstraintViolationException(ConstraintViolationException ex) {
        List<String> stacktrace = Arrays.stream(ex.getStackTrace())
            .map(StackTraceElement::toString)
            .collect(Collectors.toList());
        ApiErrorResponse response = new ApiErrorResponse("Некорректные параметры запроса",
            "400", ex.getClass().getSimpleName(), ex.getMessage(), stacktrace);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
