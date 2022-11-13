package com.hung.sneakery.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="wards")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Ward {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ward_name")
    private String name;

    @ManyToOne
    @JoinColumn(name="district_id")
    @JsonIgnore
    private District district;
}
