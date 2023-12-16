package com.hung.sneakery.config;

import com.hung.sneakery.dto.response.BaseResponse;
import com.hung.sneakery.dto.response.DataResponse;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@ControllerAdvice
public class ResponseHandler implements ResponseBodyAdvice {

    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return returnType.getContainingClass().getPackage().getName().contains("com.hung.sneakery.controller");
    }


    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if (body instanceof BaseResponse) {
            return body;
        }
//        if (body instanceof Page) {
//            return commonConverter.convertResponsePaging((Page<?>) body, true, Strings.EMPTY);
//        }
//        if (body instanceof String) {
//            return body;
//        }
        return new DataResponse<>(body);
    }
}
