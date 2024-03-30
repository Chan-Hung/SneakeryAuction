package com.hung.sneakery.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
@Builder
public class BidCreateRequest {

    @JsonProperty("name")
    @NotBlank
    private String name;

    @JsonProperty("categoryId")
    @NotNull
    private Long categoryId;

    @JsonProperty("imageIds")
    @NotEmpty
    private List<Long> imageIds;

    @JsonProperty("properties")
    @NotEmpty
    private Map<String, String> properties;

    @JsonProperty("bidClosingDateTime")
    @NotNull
    private LocalDateTime bidClosingDateTime;

    @JsonProperty("priceStart")
    @NotNull
    private Long priceStart;

    @JsonProperty("stepBid")
    @NotNull
    private Long stepBid;
}
