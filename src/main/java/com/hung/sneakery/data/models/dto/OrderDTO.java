package com.hung.sneakery.data.models.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDTO {
    private Long id;
    private ProductDTO product;
    private Long priceWin;
}
