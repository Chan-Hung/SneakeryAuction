package com.hung.sneakery.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
public class ProductDetailedDTO {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("properties")
    private Map<String, String> properties;

    @JsonProperty("description")
    private String description;

    @JsonProperty("startPrice")
    private Long startPrice;

    @JsonProperty("currentPrice")
    private Long currentPrice;

    @JsonProperty("bidIncrement")
    private Long bidIncrement;

    @JsonProperty("imagePath")
    private List<String> imagePath;

    @JsonProperty("category")
    private String category;

    @JsonProperty("bidClosingDate")
    private LocalDateTime bidClosingDate;
}