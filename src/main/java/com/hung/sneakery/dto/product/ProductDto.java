package com.hung.sneakery.dto.product;

import com.hung.sneakery.model.Product;
import com.hung.sneakery.model.ProductImage;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProductDto {
    private Long id;

    private String name;

    private String condition;

    private Long startPrice;

    private List<String> imagePath;

    private String category;

    private String brand;

    private String color;

    private String size;

    private Date endBid;

    public ProductDto(Product product){
        this.setId(product.getId());
        this.setName(product.getName());
        this.setCondition(product.getCondition());
        this.setStartPrice(product.getProductDescription().getPrice());
        List<String> imageList = new ArrayList<String>();
        for (ProductImage productImage: product.getProductImage()) {
            String imagePathSingle = productImage.getPath();
            imageList.add(imagePathSingle);
        }
        this.setImagePath(imageList);
        this.setCategory(product.getProductCategory().getTypeName());
        this.setBrand(product.getProductDescription().getBrand());
        this.setColor(product.getProductDescription().getColor());
        this.setSize(product.getProductDescription().getSize());
        this.setEndBid(product.getBid().getEnd_bid());
    }
    public ProductDto() {
    }

    public ProductDto(Long id, String name, String condition, Long startPrice, List<String> imagePath, String category, String brand, String color, String size, Date endBid) {
        this.id = id;
        this.name = name;
        this.condition = condition;
        this.startPrice = startPrice;
        this.imagePath = imagePath;
        this.category = category;
        this.brand = brand;
        this.color = color;
        this.size = size;
        this.endBid = endBid;
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

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public Long getStartPrice() {
        return startPrice;
    }

    public void setStartPrice(Long startPrice) {
        this.startPrice = startPrice;
    }

    public List<String> getImagePath() {
        return imagePath;
    }

    public void setImagePath(List<String> imagePath) {
        this.imagePath = imagePath;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Date getEndBid() {
        return endBid;
    }

    public void setEndBid(Date endBid) {
        this.endBid = endBid;
    }
}
