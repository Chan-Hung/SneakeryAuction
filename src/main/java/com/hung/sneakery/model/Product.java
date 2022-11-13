package com.hung.sneakery.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hung.sneakery.model.datatype.ECondition;
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
    private ProductCategory productCategory;

    @OneToOne
    @JoinColumn(name = "product_description_id")
    private ProductDescription productDescription;

    //mappedBy must have the same name as @ManyToOne variable
    //in ProductImage.java class
    //One Product have Many Images => FK is in image
    @OneToMany(mappedBy = "product")
    private List<ProductImage> productImage;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "bid_id")
    private Bid bid;

    @ManyToOne
    @JoinColumn(name="seller_id", nullable = false)
    private User user;
}
