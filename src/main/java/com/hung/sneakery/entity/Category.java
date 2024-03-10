package com.hung.sneakery.entity;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;
import java.util.Map;

@Entity
@Table(name = "category")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TypeDefs({@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)})
public class Category extends AbstractCommonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Type(type = "jsonb")
    @Column(name = "property", columnDefinition = "jsonb")
    private Map<String, Object> property;
}
