package com.hung.sneakery.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
@Builder
public class BidDTO {
    @NotNull
    private Long bidId;

    @NotNull
    private LocalDate bidStartingDate;

    @NotNull
    private Long priceStart;

    @NotNull
    private Long stepBid;

    //@NotNull
    private Long priceWin;

    @NotNull
    private ProductDTO product;
}
