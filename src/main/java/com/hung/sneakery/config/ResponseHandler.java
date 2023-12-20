package com.hung.sneakery.config;

import com.hung.sneakery.dto.response.BaseResponse;
import com.hung.sneakery.dto.response.DataResponse;
import com.hung.sneakery.dto.response.PageResponse;
import org.springframework.core.MethodParameter;
import org.springframework.data.domain.Page;
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
        if (body instanceof Page) {
            return new PageResponse<>(
                    ((Page) body).getContent(),
                    ((Page) body).getNumber() + 1,
                    ((Page) body).getSize(),
                    ((Page) body).getTotalElements(),
                    ((Page) body).getTotalPages());
        }
        return new DataResponse<>(body);
    }
}
