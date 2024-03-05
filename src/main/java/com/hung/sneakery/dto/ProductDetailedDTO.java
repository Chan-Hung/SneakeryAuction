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

    //Format date time with JsonFormat
    //https://www.baeldung.com/jackson-jsonformat
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:00 dd/MM/yyyy")
    @JsonProperty("bidClosingDate")
    private LocalDateTime bidClosingDate;
}