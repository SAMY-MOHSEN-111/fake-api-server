package com.example.fakeapiserver.exception;

import com.example.fakeapiserver.entity.APIResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleQuestionNotFound(RuntimeException e) {
        return new ResponseEntity<>(
                APIResponse.builder()
                        .success(false)
                        .timestamp(LocalDateTime.now())
                        .errors(List.of(e.getMessage()))
                        .build(), NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationException(MethodArgumentNotValidException e) {
        List<String> errors = new ArrayList<>();
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        for (FieldError error : fieldErrors) {
            errors.add(error.getDefaultMessage());
        }
        return new ResponseEntity<>(
                APIResponse.builder()
                        .success(false)
                        .timestamp(LocalDateTime.now())
                        .errors(errors)
                        .build(), BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception e) {
        return new ResponseEntity<>(
                APIResponse.builder()
                        .success(false)
                        .timestamp(LocalDateTime.now())
                        .errors(List.of("Something went wrong please contact the admin"))
                        .build(), BAD_REQUEST);
    }
}
