package com.hung.sneakery.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDTO {
    private Long id;
    private String name;
    private Long startPrice;
    private String imagePath;
    private String username;
    //Format date time with JsonFormat
    //https://www.baeldung.com/jackson-jsonformat
    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:00 dd/MM/yyyy")
    private LocalDateTime bidClosingDate;

}
