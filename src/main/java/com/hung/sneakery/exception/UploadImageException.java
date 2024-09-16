package com.hung.sneakery.exception;

public class UploadImageException extends RuntimeException {

    public UploadImageException(String errorMessage) {
        super(errorMessage);
    }
}
