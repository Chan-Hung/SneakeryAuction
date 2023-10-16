package com.hung.sneakery.exception;

public class TransactionException extends RuntimeException {

    public TransactionException(String errorMessage) {
        super(errorMessage);
    }
}
