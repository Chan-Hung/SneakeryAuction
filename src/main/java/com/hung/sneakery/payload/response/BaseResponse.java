package com.hung.sneakery.payload.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
public class BaseResponse {
    @NonNull
    private Boolean success;

    @NonNull
    private String message;

    public BaseResponse(){
        this.success = true;
        this.message = "";
    }
}
