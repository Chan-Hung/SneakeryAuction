package com.hung.sneakery.exception;

public class AuthenticationException extends RuntimeException {

    public AuthenticationException(String message) {
        super((message));
    }
}
