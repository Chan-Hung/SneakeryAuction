package com.hung.sneakery.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@Builder
public class SignInRequest {

    @JsonProperty("email")
    @NotBlank
    @Email
    private String email;

    @JsonProperty("password")
    @NotBlank
    private String password;
}
