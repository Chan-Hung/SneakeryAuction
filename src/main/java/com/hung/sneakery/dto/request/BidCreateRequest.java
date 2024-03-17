package com.hung.sneakery.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.Map;

@Data
@Builder
public class BidCreateRequest {

    @JsonProperty("name")
    @NotBlank
    private String name;

    @JsonProperty("categoryId")
    @NotBlank
    private Long categoryId;

    @JsonProperty("properties")
    private Map<String, String> properties;

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
