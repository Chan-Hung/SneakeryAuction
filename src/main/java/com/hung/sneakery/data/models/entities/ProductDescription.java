package com.hung.sneakery.data.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="product_description")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDescription implements Serializable {
    //Using a Shared Primary Key
    //In this strategy, instead of creating a new column product_description_id, we'll mark the primary key column (product_id) of the product_description table as the foreign key to the products table (Primary key as a foreign key)
    @Id
    @Column(name="product_id")
    private Long id;

    @Column(name="brand")
    private String brand;

    @Column(name="color")
    private String color;

    @Column(name="size")
    private Integer size;

    //Using a Shared Primary Key - Behaves as a foreign key
    @OneToOne
    @MapsId
    @JoinColumn(name = "product_id")
    @JsonIgnore
    private Product product;
}
