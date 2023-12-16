package com.hung.sneakery.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
@Builder
public class SignUpRequest {

    @JsonProperty("username")
    @NotBlank
    @Size(min = 3, max = 20)
    private String username;

    @JsonProperty("email")
    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    @JsonProperty("role")
    //Can be blank
    private Set<String> role;

    @JsonProperty("password")
    //Custom message exception
    @NotBlank
    @Size(min = 6, max = 40)
    private String password;
}
