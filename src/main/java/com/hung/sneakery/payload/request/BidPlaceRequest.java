package com.hung.sneakery.payload.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class BidPlaceRequest {
    @NotBlank
    private Long productId;

    @NotBlank
    private Long amount;
}
