package com.hung.sneakery.entity;


import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "address")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Address implements Serializable {

    @Id
    @Column(name = "user_id")
    private Long id;

    @Column(name = "home_number")
    private String homeNumber;

    @Column(name = "city_code")
    private Integer cityCode;

    @Column(name = "ward_code")
    private Integer wardCode;

    @Column(name = "district_code")
    private Integer districtCode;

    @OneToOne (fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;
}
