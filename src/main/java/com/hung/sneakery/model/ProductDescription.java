package com.hung.sneakery.model;

import javax.persistence.*;

@Entity
@Table(name="product_description")
public class ProductDescription {
    @Id
    private Long id;

    @Column(name="brand")
    private String brand;

    @Column(name="color")
    private String color;

    @Column(name="size")
    private String size;

    @Column(name = "price")
    private Long price;

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public ProductDescription() {
    }

    public ProductDescription(Long id, String brand, String color, String size, Long price) {
        this.id = id;
        this.brand = brand;
        this.color = color;
        this.size = size;
        this.price = price;
    }
}
