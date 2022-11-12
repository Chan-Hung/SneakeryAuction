package com.hung.sneakery.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name="products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_name")
    private String name;

    //'Condition' is a reserved word
    //which mean can not set as a property name
    //=> use "condition_description" instead
    @Column(name="condition_description")
    private String condition;

    @Column(name="bid_closing_date")
    private LocalDateTime bidClosingDateTime;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_category_id", nullable = false)
    @JsonIgnore
    private ProductCategory productCategory;

    @OneToOne
    @JoinColumn(name = "product_description_id")
    private ProductDescription productDescription;

    //mappedBy must have the same name as @ManyToOne variable
    //in ProductImage.java class
    //One Product have Many Images => FK is in image
    @OneToMany(mappedBy = "product")
    private List<ProductImage> productImage;

    @OneToOne
    @JoinColumn(name = "bid_id")
    private Bid bid;

    @ManyToOne
    @JoinColumn(name="seller_id", nullable = false)
    private User user;

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

    public LocalDateTime getBidClosingDateTime() {
        return bidClosingDateTime;
    }

    public void setBidClosingDateTime(LocalDateTime bidClosingDateTime) {
        this.bidClosingDateTime = bidClosingDateTime;
    }

    public ProductCategory getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(ProductCategory productCategory) {
        this.productCategory = productCategory;
    }

    public ProductDescription getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(ProductDescription productDescription) {
        this.productDescription = productDescription;
    }

    public List<ProductImage> getProductImage() {
        return productImage;
    }

    public void setProductImage(List<ProductImage> productImage) {
        this.productImage = productImage;
    }

    public Bid getBid() {
        return bid;
    }

    public void setBid(Bid bid) {
        this.bid = bid;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Product() {
    }

    public Product(Long id, String name, String condition) {
        this.id = id;
        this.name = name;
        this.condition = condition;
    }
}
