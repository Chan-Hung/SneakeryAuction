package com.hung.sneakery.entity;


import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "address")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Address extends AbstractCommonEntity {

    @Id
    @Column(name = "user_id")
    private Long id;

    @Column
    private String homeNumber;

    @Column
    private Integer cityCode;

    @Column
    private Integer wardCode;

    @Column
    private Integer districtCode;

    @Column
    private String phoneNumber;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;
}
