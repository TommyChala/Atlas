package com.Hub.system.advice;

import com.Hub.system.exception.AccountAttributeNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class SystemExceptionHandler {
    @ExceptionHandler(AccountAttributeNotFoundException.class)
    public ResponseEntity<String> handleAccountAttributeNotFound (AccountAttributeNotFoundException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
