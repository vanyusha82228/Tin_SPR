package edu.java.scrapper.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.java.controllers.GlobalExceptionHandler;
import edu.java.dto.responseDto.ApiErrorResponse;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler globalExceptionHandler;

    @BeforeEach
    void setUp() {
        globalExceptionHandler = new GlobalExceptionHandler();
    }

    @Test
    void testHandleConstraintViolationException() {
        ConstraintViolationException exception = new ConstraintViolationException("Test message", Collections.emptySet());

        ResponseEntity<ApiErrorResponse> responseEntity = globalExceptionHandler.handleConstraintViolationException(exception);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());

        ApiErrorResponse apiErrorResponse = responseEntity.getBody();
        assertEquals("Некорректные параметры запроса", apiErrorResponse.getDescription());
        assertEquals("400", apiErrorResponse.getCode());
        assertEquals(exception.getClass().getSimpleName(), apiErrorResponse.getExceptionName());
        assertEquals("Test message", apiErrorResponse.getExceptionMessage());
    }
}
