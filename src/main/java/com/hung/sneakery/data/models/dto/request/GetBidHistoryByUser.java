package com.hung.sneakery.data.models.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Getter
@Setter
public class GetBidHistoryByUser {
    @NotBlank
    private Long productId;

    @NotBlank
    private Long price;

    @NotBlank
    private LocalDateTime createdAt;
}
