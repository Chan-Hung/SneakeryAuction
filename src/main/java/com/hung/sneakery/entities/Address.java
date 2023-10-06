package com.hung.sneakery.entities;


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

    @Column(name = "city_id")
    private String city;

    @Column(name = "ward_id")
    private String ward;

    @Column(name = "district_id")
    private String district;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;
}
