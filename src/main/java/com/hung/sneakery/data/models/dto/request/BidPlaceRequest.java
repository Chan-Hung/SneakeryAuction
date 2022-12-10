package com.hung.sneakery.data.models.dto.request;

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
