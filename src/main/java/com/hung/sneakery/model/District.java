package com.hung.sneakery.model;

import javax.persistence.*;

@Entity
@Table(name="districts")
public class District {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "district_name")
    private String name;

    @ManyToOne
    @JoinColumn(name="city_id")
    private City city;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public District() {
    }

    public District(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
