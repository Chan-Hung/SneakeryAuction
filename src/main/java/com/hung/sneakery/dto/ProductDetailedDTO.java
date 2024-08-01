package com.hung.sneakery.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
public class ProductDetailedDTO implements Serializable {

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

    @JsonProperty("holder")
    private String holder;

    @JsonProperty("seller")
    private UserDTO seller;

    @JsonProperty("bidIncrement")
    private Long bidIncrement;

    @JsonProperty("imagePath")
    private List<String> imagePath;

    @JsonProperty("category")
    private String category;

    @JsonProperty("bidCreatedDate")
    private LocalDateTime bidCreatedDate;

    @JsonProperty("bidClosingDate")
    private LocalDateTime bidClosingDate;
}