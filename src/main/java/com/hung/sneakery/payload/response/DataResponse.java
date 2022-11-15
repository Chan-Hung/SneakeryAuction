package com.hung.sneakery.payload.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DataResponse<T> extends BaseResponse {
    private T data;

    public DataResponse(Boolean success, String message, T data){
        super();
        this.data = data;
    }

    public DataResponse(T data){
        super(true, "");
        this.data = data;
    }

    public DataResponse(){
        super();
    }


}
