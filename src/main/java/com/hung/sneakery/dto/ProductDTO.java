package com.hung.sneakery.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ProductDTO {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("startPrice")
    private Long startPrice;

    @JsonProperty("imagePath")
    private String imagePath;

    @JsonProperty("userName")
    private String username;

    //Format date time with JsonFormat
    //https://www.baeldung.com/jackson-jsonformat
    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:00 dd/MM/yyyy")
    @JsonProperty("bidClosingDate")
    private LocalDateTime bidClosingDate;
}
