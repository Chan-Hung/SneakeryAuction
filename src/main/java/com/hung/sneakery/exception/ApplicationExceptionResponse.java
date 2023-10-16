package com.hung.sneakery.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class ApplicationExceptionResponse {

    private String message;

    private HttpStatus status;
}
