package com.hung.sneakery.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "medias")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Media extends AbstractCommonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_image_id_seq")
    @SequenceGenerator(name = "product_image_id_seq", sequenceName = "product_image_seq", allocationSize = 1)
    private Long id;

    @Column
    private String path;

    @Column
    private Boolean isThumbnail;
}
