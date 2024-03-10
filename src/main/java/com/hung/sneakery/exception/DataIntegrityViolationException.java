package com.hung.sneakery.exception;

public class DataIntegrityViolationException extends RuntimeException {

    public DataIntegrityViolationException(String errorMessage) {
        super(errorMessage);
    }
}
