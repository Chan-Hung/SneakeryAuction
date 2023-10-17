package com.hung.sneakery.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderDTO {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("product")
    private ProductDTO product;

    @JsonProperty("priceWin")
    private Long priceWin;
}
