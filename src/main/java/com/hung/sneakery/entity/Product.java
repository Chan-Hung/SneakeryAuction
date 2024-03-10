package com.hung.sneakery.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.List;
import java.util.Map;

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

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    @JsonIgnore
    private Category category;

    @Type(type = "jsonb")
    @Column(name = "properties", columnDefinition = "jsonb")
    private Map<String, String> properties;

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
