package com.hung.sneakery.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity
@Table(name = "product_image")
public class ProductImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="image_path")
    private String path;

    @Column(name="is_thumbnail")
    private Boolean isThumbnail;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Boolean getThumbnail() {
        return isThumbnail;
    }

    public void setThumbnail(Boolean thumbnail) {
        isThumbnail = thumbnail;
    }

    public ProductImage() {
    }

    public ProductImage(Long id, String path, Boolean isThumbnail, Product product) {
        this.id = id;
        this.path = path;
        this.isThumbnail = isThumbnail;
        this.product = product;
    }

    public ProductImage(Long id, String path) {
        this.id = id;
        this.path = path;
    }

    @ManyToOne
    @JoinColumn(name="product_id", nullable = false)
    @JsonBackReference
    private Product product;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
