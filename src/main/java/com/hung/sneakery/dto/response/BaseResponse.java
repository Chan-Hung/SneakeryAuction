package com.hung.sneakery.dto.response;

import lombok.*;

@Data
@Builder
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
