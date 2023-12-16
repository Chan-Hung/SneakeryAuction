package com.hung.sneakery.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@Builder
public class BidDTO {

    @JsonProperty("bidId")
    @NotNull
    private Long bidId;

    @JsonProperty("bidStartingDate")
    @NotNull
    private LocalDate bidStartingDate;

    @JsonProperty("priceStart")
    @NotNull
    private Long priceStart;

    @JsonProperty("stepBid")
    @NotNull
    private Long stepBid;

    @JsonProperty("priceWin")
    //@NotNull
    private Long priceWin;

    @JsonProperty("product")
    @NotNull
    private ProductDTO product;
}
