package com.hung.sneakery.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class OrderDTO {
    private Long id;
    private ProductDTO product;
    private Long priceWin;
}
