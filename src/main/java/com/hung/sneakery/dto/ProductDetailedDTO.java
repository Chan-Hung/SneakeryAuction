package com.hung.sneakery.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hung.sneakery.enums.ECondition;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class ProductDetailedDTO {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("condition")
    private ECondition condition;

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

    @JsonProperty("brand")
    private String brand;

    @JsonProperty("color")
    private String color;

    @JsonProperty("size")
    private Integer size;

    //Format date time with JsonFormat
    //https://www.baeldung.com/jackson-jsonformat
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:00 dd/MM/yyyy")
    @JsonProperty("bidClosingDate")
    private LocalDateTime bidClosingDate;
}
