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

    public ProductDescription(Long id, String color) {
        this.id = id;
        this.color = color;
    }
}
