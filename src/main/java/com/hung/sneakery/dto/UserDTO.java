package com.hung.sneakery.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@Builder
public class UserDTO {

    @JsonProperty("id")
    @NotNull
    private Long id;

    @JsonProperty("username")
    @NotNull
    private String username;

    @JsonProperty("email")
    @NotNull
    private String email;

    @JsonProperty("isActive")
    @NotNull
    private Boolean isActive;

    @JsonProperty("address")
    private AddressDTO address;
}
