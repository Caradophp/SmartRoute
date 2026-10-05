package com.faesa.smartRoute.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(RecordNotFoundException.class)
    public ResponseEntity<Map<String, String>> recordNotFoundHandler(RecordNotFoundException exception) {
        Map<String, String> params = new HashMap<>();
        params.put("info", exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(params);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(BusinessException.class)
    public ResponseEntity<Map<String, String>> recordNotFoundHandler(BusinessException exception) {
        Map<String, String> params = new HashMap<>();
        params.put("aviso", exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(params);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> recordNotFoundHandler(Exception exception) {
        Map<String, String> params = new HashMap<>();
        params.put("erro", exception.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(params);
    }
}
