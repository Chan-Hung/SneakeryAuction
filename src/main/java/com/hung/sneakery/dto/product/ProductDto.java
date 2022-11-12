package com.hung.sneakery.dto.product;

import com.hung.sneakery.model.Product;
import com.hung.sneakery.model.ProductImage;

import java.time.LocalDateTime;

public class ProductDto {
    private Long id;
    private String name;
    private Long startPrice;
    private String imagePath;
    private String username;
    private LocalDateTime bidClosingDate;

    public ProductDto(Product product){
        this.setId(product.getId());
        this.setName(product.getName());
        for (ProductImage productImage: product.getProductImage())
            if(productImage.getThumbnail())
                this.setImagePath(productImage.getPath());
        this.setStartPrice(product.getProductDescription().getPrice());
        this.setUsername(product.getUser().getUsername());
        this.setBidClosingDate(product.getBidClosingDateTime());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getStartPrice() {
        return startPrice;
    }

    public void setStartPrice(Long startPrice) {
        this.startPrice = startPrice;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public LocalDateTime getBidClosingDate() {
        return bidClosingDate;
    }

    public void setBidClosingDate(LocalDateTime bidClosingDate) {
        this.bidClosingDate = bidClosingDate;
    }
}
