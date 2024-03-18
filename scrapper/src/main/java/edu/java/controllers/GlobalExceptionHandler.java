package edu.java.controllers;

import edu.java.dto.response.ApiErrorResponse;
import edu.java.exeption.ChatAlreadyRegisteredException;
import edu.java.exeption.ChatNotFoundException;
import edu.java.exeption.LinkAlreadyAddedException;
import java.util.Arrays;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final String BAD_REQUEST_CODE = "400";
    private static final String NOT_FOUND_CODE = "404";

    @ExceptionHandler(ChatAlreadyRegisteredException.class)
    public ResponseEntity<ApiErrorResponse> handleChatAlreadyRegisteredException(ChatAlreadyRegisteredException ex) {
        ApiErrorResponse errorResponse = new ApiErrorResponse("чат уже зарегестрирован", BAD_REQUEST_CODE,
            ex.getClass().getName(), ex.getMessage(), extractStackTrace(ex)
        );
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(LinkAlreadyAddedException.class)
    public ResponseEntity<ApiErrorResponse> handleLinkAlreadyAddedException(LinkAlreadyAddedException ex) {
        ApiErrorResponse errorResponse = new ApiErrorResponse("ссылка уже добавлена", BAD_REQUEST_CODE,
            ex.getClass().getName(), ex.getMessage(), extractStackTrace(ex));
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(ChatNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleChatNotFoundException(ChatNotFoundException ex) {
        ApiErrorResponse errorResponse = new ApiErrorResponse("чат не найден", NOT_FOUND_CODE,
            ex.getClass().getName(), ex.getMessage(), extractStackTrace(ex));
        return ResponseEntity.notFound().build();
    }

    private List<String> extractStackTrace(Exception ex) {
        return Arrays.stream(ex.getStackTrace())
            .map(StackTraceElement::toString)
            .toList();
    }

}

