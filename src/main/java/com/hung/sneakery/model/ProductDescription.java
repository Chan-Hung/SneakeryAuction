package com.hung.sneakery.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="product_description")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDescription {
    @Id
    private Long id;

    @Column(name="brand")
    private String brand;

    @Column(name="color")
    private String color;

    @Column(name="size")
    private Integer size;
}
