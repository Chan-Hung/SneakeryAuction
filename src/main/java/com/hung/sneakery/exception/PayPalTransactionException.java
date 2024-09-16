package com.hung.sneakery.exception;

public class PayPalTransactionException extends RuntimeException {

    public PayPalTransactionException(String errorMessage) {
        super(errorMessage);
    }
}
