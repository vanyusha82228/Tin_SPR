package edu.java.bot.controllers;

import edu.java.bot.dto.response.ApiErrorResponse;
import edu.java.bot.exeption.ChatAlreadyRegisteredException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import edu.java.bot.exeption.ChatNotFoundException;
import edu.java.bot.exeption.LinkAlreadyAddedException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ChatAlreadyRegisteredException.class)
    public ResponseEntity<ApiErrorResponse> handleChatAlreadyRegisteredException(ChatAlreadyRegisteredException ex) {
        List<String> stacktrace = Arrays.stream(ex.getStackTrace())
            .map(StackTraceElement::toString)
            .collect(Collectors.toList());
        ApiErrorResponse errorResponse = new ApiErrorResponse("чат уже зарегестрирован", "400",
            ex.getClass().getName(), ex.getMessage(), stacktrace
        );
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(LinkAlreadyAddedException.class)
    public ResponseEntity<ApiErrorResponse> handleLinkAlreadyAddedException(LinkAlreadyAddedException ex) {
        List<String> stacktrace = Arrays.stream(ex.getStackTrace())
            .map(StackTraceElement::toString)
            .collect(Collectors.toList());
        ApiErrorResponse errorResponse = new ApiErrorResponse("ссылка уже добавлена", "400",
            ex.getClass().getName(), ex.getMessage(), stacktrace);
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(ChatNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleChatNotFoundException(ChatNotFoundException ex) {
        List<String> stacktrace = Arrays.stream(ex.getStackTrace())
            .map(StackTraceElement::toString)
            .collect(Collectors.toList());
        ApiErrorResponse errorResponse = new ApiErrorResponse("чат не найден", "404",
            ex.getClass().getName(), ex.getMessage(), stacktrace);
        return ResponseEntity.notFound().build();
    }
}

