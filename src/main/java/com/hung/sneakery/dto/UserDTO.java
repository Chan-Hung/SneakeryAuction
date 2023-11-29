package com.hung.sneakery.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
