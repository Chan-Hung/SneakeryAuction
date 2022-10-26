package com.hung.sneakery.model;

import javax.persistence.*;

@Entity
@Table(name="wards")
public class Ward {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ward_name")
    private String name;

    @ManyToOne
    @JoinColumn(name="city_id")
    private District district;

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

    public District getDistrict() {
        return district;
    }

    public void setDistrict(District district) {
        this.district = district;
    }

    public Ward() {
    }

    public Ward(Long id, String Name) {
        this.id = id;
        this.name = Name;
    }
}
