package com.hung.sneakery.data.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hung.sneakery.utils.enums.ECondition;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="products")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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
    @Enumerated(EnumType.STRING)
    private ECondition condition;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_category_id", nullable = false)
    @JsonIgnore
    private Category category;

    @OneToOne(mappedBy="product")
    @PrimaryKeyJoinColumn
    private ProductDescription productDescription;

    //mappedBy must have the same name as @ManyToOne variable
    //in ProductImageRepository.java class
    //One Product have Many Images => FK is in image
    @OneToMany(mappedBy = "product")
    private List<ProductImage> productImage;

    @OneToOne(mappedBy="product")
    @PrimaryKeyJoinColumn
    private Bid bid;

    @ManyToOne
    @JoinColumn(name="seller_id", nullable = false)
    private User user;
}
