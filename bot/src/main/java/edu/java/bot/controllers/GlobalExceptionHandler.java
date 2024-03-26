package edu.java.bot.controllers;


import edu.java.bot.dto.responseDto.ApiErrorResponse;
import jakarta.validation.ConstraintViolationException;
import java.util.Arrays;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiErrorResponse> handleConstraintViolationException(ConstraintViolationException ex) {
        ApiErrorResponse response = new ApiErrorResponse("Некорректные параметры запроса",
            "400", ex.getClass().getSimpleName(), ex.getMessage(), extractStackTrace(ex));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    private List<String> extractStackTrace(Exception ex) {
        return Arrays.stream(ex.getStackTrace())
            .map(StackTraceElement::toString)
            .toList();
    }
}
