package com.hung.sneakery.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.hung.sneakery.enums.ECondition;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "products")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product extends AbstractCommonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    //'Condition' is a reserved word -> can not set as a property name
    //=> use "condition_description" instead
    @Column(name = "condition_description")
    @Enumerated(EnumType.STRING)
    private ECondition condition;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    @JsonIgnore
    private Category category;

    @Column
    private String brand;

    @Column
    private String color;

    @Column
    private Integer size;

    //mappedBy must have the same name as @ManyToOne variable
    //in ProductImageRepository.java class
    //One Product have Many Images => FK is in image
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "product")
    private List<ProductImage> productImage;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "product")
    @PrimaryKeyJoinColumn
    @JsonManagedReference
    private Bid bid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id", nullable = false)
    private User user;
}
