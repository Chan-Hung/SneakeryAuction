package com.hung.sneakery.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hung.sneakery.enums.BidOutcome;
import com.hung.sneakery.enums.PaymentStatus;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Builder
public class BidDTO {

    @JsonProperty("bidId")
    @NotNull
    private Long bidId;

    @JsonProperty("bidStartingDate")
    @NotNull
    private LocalDateTime bidStartingDate;

    @JsonProperty("priceStart")
    @NotNull
    private Long priceStart;

    @JsonProperty("stepBid")
    @NotNull
    private Long stepBid;

    @JsonProperty("priceWin")
    private Long priceWin;

    @JsonProperty("bidOutCome")
    private BidOutcome bidOutCome;

    @JsonProperty("sellerPaymentStatus")
    private PaymentStatus sellerPaymentStatus;

    @JsonProperty("winner")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private WinnerDTO winner;

    @JsonProperty("product")
    @NotNull
    private ProductDTO product;
}
