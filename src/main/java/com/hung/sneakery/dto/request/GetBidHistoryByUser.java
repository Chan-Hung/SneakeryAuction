package com.hung.sneakery.dto.request;

import com.hung.sneakery.dto.ProductDTO;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Getter
@Setter
public class GetBidHistoryByUser {
     @NotBlank
    private Long amount;

    @NotBlank
    private LocalDateTime createdAt;

    @NotBlank
    private ProductDTO product;
}
