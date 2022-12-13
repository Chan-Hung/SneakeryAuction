package com.hung.sneakery.data.models.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "product_image")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductImage implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="image_path")
    private String path;

    @Column(name="is_thumbnail")
    private Boolean isThumbnail;

    @ManyToOne
    @JoinColumn(name="product_id", nullable = false)
    @JsonBackReference
    private Product product;
}
