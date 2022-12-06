package com.hung.sneakery.payload.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class EmailRequest {
    @NotBlank
    private String Email;
}
