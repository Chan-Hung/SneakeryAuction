package com.hung.sneakery.data.models.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class EmailRequest {
    @NotBlank
    private String Email;
}
