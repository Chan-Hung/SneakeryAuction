package com.hung.sneakery.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResetPasswordRequest {

    @JsonProperty("phoneNumber")
    private String phoneNumber;

    @JsonProperty("newPassword")
    private String newPassword;
}
