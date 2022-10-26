package com.hung.sneakery.model;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "product_category")
public class ProductCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "typeName")
    private String typeName;

    @CreationTimestamp
    @Temporal(TemporalType.DATE)
    private Date createdAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public ProductCategory() {
    }

    public ProductCategory(Long id, String typeName, Date createdAt) {
        this.id = id;
        this.typeName = typeName;
        this.createdAt = createdAt;
    }

}
