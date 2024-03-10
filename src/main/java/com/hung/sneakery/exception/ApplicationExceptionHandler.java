package com.hung.sneakery.exception;

import com.paypal.base.rest.PayPalRESTException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationExceptionHandler.class);

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handleNotFoundException(NotFoundException ex) {
        LOGGER.error("Bad Request {}", ExceptionUtils.getStackTrace(ex));
        ApplicationExceptionResponse error = new ApplicationExceptionResponse();
        error.setMessage(ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler({PayPalRESTException.class, PayPalTransactionException.class})
    public ResponseEntity<Object> handlePayPalException(PayPalRESTException ex) {
        LOGGER.error("Internal server error {}", ExceptionUtils.getStackTrace(ex));
        ApplicationExceptionResponse error = new ApplicationExceptionResponse();
        error.setMessage(ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<Object> handleAuthenticationException(AuthenticationException ex) {
        LOGGER.error("Internal server error {}", ExceptionUtils.getStackTrace(ex));
        ApplicationExceptionResponse error = new ApplicationExceptionResponse();
        error.setMessage(ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    @ExceptionHandler({BidPlacingException.class, BidCreatingException.class})
    public ResponseEntity<Object> handleBidException(BidPlacingException ex) {
        LOGGER.error("Internal server error {}", ExceptionUtils.getStackTrace(ex));
        ApplicationExceptionResponse error = new ApplicationExceptionResponse();
        error.setMessage(ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    @ExceptionHandler(UploadImageException.class)
    public ResponseEntity<Object> handleUploadImageException(UploadImageException ex) {
        LOGGER.error("Internal server error {}", ExceptionUtils.getStackTrace(ex));
        ApplicationExceptionResponse error = new ApplicationExceptionResponse();
        error.setMessage(ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        LOGGER.error("Internal server error {}", ExceptionUtils.getStackTrace(ex));
        ApplicationExceptionResponse error = new ApplicationExceptionResponse();
        error.setMessage(ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}
