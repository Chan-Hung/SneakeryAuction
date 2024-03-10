package com.hung.sneakery.exception;

public class DataIntegrityViolation extends RuntimeException {

    public DataIntegrityViolation(String errorMessage) {
        super(errorMessage);
    }
}
