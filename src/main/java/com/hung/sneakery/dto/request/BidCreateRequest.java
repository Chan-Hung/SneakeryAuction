package com.hung.sneakery.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hung.sneakery.enums.ECondition;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
@Builder
public class BidCreateRequest {

    @JsonProperty("name")
    @NotBlank
    private String name;

    @JsonProperty("condition")
    @NotBlank
    private ECondition condition;

    @JsonProperty("category")
    @NotBlank
    private String category;

    @JsonProperty("brand")
    //Product description
    @NotBlank
    private String brand;

    @JsonProperty("color")
    @NotBlank
    private String color;

    @JsonProperty("size")
    @NotBlank
    private Integer size;

    @JsonProperty("bidClosingDateTime")
    @NotBlank
    private LocalDateTime bidClosingDateTime;

    @JsonProperty("priceStart")
    @NotBlank
    private Long priceStart;

    @JsonProperty("stepBid")
    @NotBlank
    private Long stepBid;
}
