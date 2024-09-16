package com.hung.sneakery.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
public class ProductDTO implements Serializable {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("imagePath")
    private String imagePath;

    @JsonProperty("currentPrice")
    private Long currentPrice;

    @JsonProperty("holder")
    private String holder;

    @JsonProperty("bidCreatedDate")
    private LocalDateTime bidCreatedDate;

    @JsonProperty("bidClosingDate")
    private LocalDateTime bidClosingDate;

    @JsonProperty("numberOfBids")
    private Integer numberOfBids;
}
