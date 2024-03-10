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
    @Column
    private Map<String, Object> property;

    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    private Set<Product> products = new HashSet<>();
}
