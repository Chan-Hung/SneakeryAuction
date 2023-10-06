package com.hung.sneakery.dto;

import com.hung.sneakery.entity.Product;
import com.hung.sneakery.entity.ProductImage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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

    public ProductDTO(Product product){
        this.setId(product.getId());
        this.setName(product.getName());
        for (ProductImage productImage: product.getProductImage())
            if(productImage.getIsThumbnail())
                this.setImagePath(productImage.getPath());
        this.setStartPrice(product.getBid().getPriceStart());
        this.setUsername(product.getUser().getUsername());
        this.setBidClosingDate(product.getBid().getBidClosingDateTime());
    }
}
