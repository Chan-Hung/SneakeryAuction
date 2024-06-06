package com.hung.sneakery.exception;

import com.hung.sneakery.utils.SneakeryConstant;
import com.paypal.base.rest.PayPalRESTException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;

@ControllerAdvice
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationExceptionHandler.class);

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handleNotFoundException(NotFoundException ex) {
        return buildErrorResponse(ex, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<Object> handleMaxUploadSizeExceededException(MaxUploadSizeExceededException ex) {
        ApplicationExceptionResponse error = buildApplicationExceptionResponse(SneakeryConstant.PAYLOAD_TOO_LARGE, ex);
        return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE).body(error);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<Object> handleAuthenticationException(AuthenticationException ex) {
        return buildErrorResponse(ex, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler({
            PayPalRESTException.class,
            PayPalTransactionException.class,
            BidCreatingException.class,
            BidPlacingException.class,
            UploadImageException.class,
            DataIntegrityViolationException.class,
            IllegalArgumentException.class,
            ConstraintViolationException.class
    })
    public ResponseEntity<Object> handleGenericException(Exception ex) {
        return buildErrorResponse(ex, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<Object> buildErrorResponse(Exception ex, HttpStatus status) {
        LOGGER.error("Exception: {}", ExceptionUtils.getStackTrace(ex)); //NOSONAR
        ApplicationExceptionResponse error = buildApplicationExceptionResponse(ex.getMessage(), ex);
        return ResponseEntity.status(status).body(error);
    }

    private ApplicationExceptionResponse buildApplicationExceptionResponse(String message, Exception ex) {
        ApplicationExceptionResponse error = new ApplicationExceptionResponse();
        error.setMessage(message);
        error.setExceptionType(ex.getClass().getSimpleName());
        return error;
    }
}
