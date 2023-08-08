package com.hung.sneakery.data.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "wards")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Ward implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ward_name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "district_id")
    @JsonIgnore
    private District district;
}
