package com.ecomm.exception;

import org.springframework.http.ResponseEntity;

public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
}
