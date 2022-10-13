package com.hung.sneakery.payload.request;

import javax.validation.constraints.NotBlank;

public class EmailRequest {
    @NotBlank
    private String Email;

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }
}
