package edu.java.bot.controllers;

import edu.java.bot.dto.response.ApiErrorResponse;
import edu.java.bot.exeption.ChatAlreadyRegisteredException;
import edu.java.bot.exeption.ChatNotFoundException;
import edu.java.bot.exeption.LinkAlreadyAddedException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final String BAD_REQUEST_CODE = "400";
    private static final String NOT_FOUND_CODE = "404";

    @ExceptionHandler(ChatAlreadyRegisteredException.class)
    public ResponseEntity<ApiErrorResponse> handleChatAlreadyRegisteredException(ChatAlreadyRegisteredException ex) {
        List<String> stacktrace = Arrays.stream(ex.getStackTrace())
            .map(StackTraceElement::toString)
            .collect(Collectors.toList());
        ApiErrorResponse errorResponse = new ApiErrorResponse("чат уже зарегестрирован", BAD_REQUEST_CODE,
            ex.getClass().getName(), ex.getMessage(), stacktrace
        );
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(LinkAlreadyAddedException.class)
    public ResponseEntity<ApiErrorResponse> handleLinkAlreadyAddedException(LinkAlreadyAddedException ex) {
        List<String> stacktrace = Arrays.stream(ex.getStackTrace())
            .map(StackTraceElement::toString)
            .collect(Collectors.toList());
        ApiErrorResponse errorResponse = new ApiErrorResponse("ссылка уже добавлена", BAD_REQUEST_CODE,
            ex.getClass().getName(), ex.getMessage(), stacktrace);
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(ChatNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleChatNotFoundException(ChatNotFoundException ex) {
        List<String> stacktrace = Arrays.stream(ex.getStackTrace())
            .map(StackTraceElement::toString)
            .collect(Collectors.toList());
        ApiErrorResponse errorResponse = new ApiErrorResponse("чат не найден", NOT_FOUND_CODE,
            ex.getClass().getName(), ex.getMessage(), stacktrace);
        return ResponseEntity.notFound().build();
    }
}

