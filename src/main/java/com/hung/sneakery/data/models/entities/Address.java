package com.hung.sneakery.data.models.entities;


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

    @OneToOne
    @JoinColumn(name = "city_id")
    private City city;

    @OneToOne
    @JoinColumn(name = "ward_id")
    private Ward ward;

    @OneToOne
    @JoinColumn(name = "district_id")
    private District district;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;
}
