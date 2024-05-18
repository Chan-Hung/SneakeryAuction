package com.hung.sneakery.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApplicationExceptionResponse {

    private String exceptionType;

    private String message;

    private Boolean success = false;
}
