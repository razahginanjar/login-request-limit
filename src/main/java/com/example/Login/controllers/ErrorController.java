package com.example.Login.controllers;

import com.example.Login.dto.responses.CommonResponse;
import com.example.Login.dto.responses.ErrorResponse;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.time.DateTimeException;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ErrorController {
    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<CommonResponse<String>> handleEntityNotFoundException(EntityNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(
                        CommonResponse.<String>builder()
                                .data(HttpStatus.NOT_FOUND.getReasonPhrase())
                                .statusCode(HttpStatus.NOT_FOUND.value())
                                .message(ex.getLocalizedMessage())
                                .build()
                );
    }

    @ExceptionHandler(IOException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<CommonResponse<String>> handleIOException(IOException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(
                        CommonResponse.<String>builder()
                                .data(HttpStatus.BAD_REQUEST.getReasonPhrase())
                                .statusCode(HttpStatus.BAD_REQUEST.value())
                                .message(ex.getLocalizedMessage())
                                .build()
                );
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<CommonResponse<String>>  handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(
                        CommonResponse.<String>builder()
                                .data(HttpStatus.BAD_REQUEST.getReasonPhrase())
                                .statusCode(HttpStatus.BAD_REQUEST.value())
                                .message(ex.getLocalizedMessage())
                                .build()
                );
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<CommonResponse<String>> handleResponseStatusException(ResponseStatusException ex) {
        return ResponseEntity.status(ex.getStatusCode())
                .body(
                        CommonResponse.<String>builder()
                                .data(ex.getLocalizedMessage())
                                .statusCode(ex.getStatusCode().value())
                                .message("Error {}:")
                                .build()
                );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<CommonResponse<ErrorResponse>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult().getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(", "));

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(errorMessage);
        errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        errorResponse.setTimestamp(System.currentTimeMillis());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(
                        CommonResponse.<ErrorResponse>builder()
                                .data(errorResponse)
                                .statusCode(HttpStatus.BAD_REQUEST.value())
                                .message(ex.getLocalizedMessage())
                                .build()
                );
    }
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<CommonResponse<ErrorResponse>> handleAccessDeniedException(AccessDeniedException ex) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.FORBIDDEN.getReasonPhrase()+ ex.getMessage(),
                HttpStatus.FORBIDDEN.value(),
                System.currentTimeMillis()
        );
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(
                        CommonResponse.<ErrorResponse>builder()
                                .data(errorResponse)
                                .statusCode(HttpStatus.FORBIDDEN.value())
                                .message(ex.getLocalizedMessage())
                                .build()
                );
    }

    @ExceptionHandler(DateTimeException.class)
    public ResponseEntity<CommonResponse<String>> dateTimeException(DateTimeException exception)
    {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(
                        CommonResponse.<String>builder()
                                .data(HttpStatus.BAD_REQUEST.getReasonPhrase())
                                .statusCode(HttpStatus.BAD_REQUEST.value())
                                .message(exception.getLocalizedMessage())
                                .build()
                );
    }
}
