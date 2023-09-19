package com.hung.sneakery.data.models.dto.request;

import com.hung.sneakery.utils.enums.ECondition;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Getter
@Setter
public class BidCreateRequest {
    @NotBlank
    private String name;

    @NotBlank
    private ECondition condition;

    @NotBlank
    private String category;

    //Product description
    @NotBlank
    private String brand;

    @NotBlank
    private String color;

    @NotBlank
    private Integer size;

    @NotBlank
    private LocalDateTime bidClosingDateTime;

    @NotBlank
    private Long priceStart;

    @NotBlank
    private Long stepBid;
}
