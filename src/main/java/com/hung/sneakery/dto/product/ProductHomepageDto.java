package com.hung.sneakery.dto.product;

import com.hung.sneakery.model.Product;
import com.hung.sneakery.model.ProductImage;

public class ProductHomepageDto {
    private Long id;
    private String name;
    private Long startPrice;
    private String imagePath;
    private String username;

    public ProductHomepageDto(Product product){
        this.setId(product.getId());
        this.setName(product.getName());
        for (ProductImage productImage: product.getProductImage()) {
            this.setImagePath(productImage.getPath());
            break;
        }
        this.setStartPrice(product.getProductDescription().getPrice());
        this.setUsername(product.getUser().getUsername());
    }

    public ProductHomepageDto() {
    }

    public ProductHomepageDto(Long id, String name, Long startPrice, String imagePath, String username) {
        this.id = id;
        this.name = name;
        this.startPrice = startPrice;
        this.imagePath = imagePath;
        this.username = username;
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
}
