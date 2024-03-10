package com.hung.sneakery.entity;

import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Entity
@Table(name = "category")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Category extends AbstractCommonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Type(type = "jsonb")
    @Column(name = "property", columnDefinition = "jsonb")
    private Map<String, Object> property;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "category")
    private Set<Product> products = new HashSet<>();
}
