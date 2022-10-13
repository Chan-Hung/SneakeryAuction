package com.hung.sneakery.payload.response;

public class CheckEmailResponse {
    private Boolean isEmailExisted;

    public Boolean getEmailExisted() {
        return isEmailExisted;
    }

    public void setEmailExisted(Boolean emailExisted) {
        isEmailExisted = emailExisted;
    }

    public CheckEmailResponse(Boolean isEmailExisted) {
        this.isEmailExisted = isEmailExisted;
    }
}
