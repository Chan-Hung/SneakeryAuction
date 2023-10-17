package com.hung.sneakery.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DataResponse<T> extends BaseResponse {
    private T data;

    public DataResponse(T data) {
        super(true, "");
        this.data = data;
    }
}
