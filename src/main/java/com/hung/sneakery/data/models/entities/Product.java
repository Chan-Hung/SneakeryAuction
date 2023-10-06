package com.hung.sneakery.data.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.hung.sneakery.enums.ECondition;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "products")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_name")
    private String name;

    //'Condition' is a reserved word
    //which mean can not set as a property name
    //=> use "condition_description" instead
    @Column(name = "condition_description")
    @Enumerated(EnumType.STRING)
    private ECondition condition;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_category_id", nullable = false)
    @JsonIgnore
    private Category category;

    @OneToOne(mappedBy = "product")
    @PrimaryKeyJoinColumn
    private ProductDescription productDescription;

    //mappedBy must have the same name as @ManyToOne variable
    //in ProductImageRepository.java class
    //One Product have Many Images => FK is in image
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<ProductImage> productImage;

    @OneToOne(mappedBy = "product")
    @PrimaryKeyJoinColumn
    @JsonManagedReference
    private Bid bid;

    @ManyToOne
    @JoinColumn(name = "seller_id", nullable = false)
    private User user;
}
