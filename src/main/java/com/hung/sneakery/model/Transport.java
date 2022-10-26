package com.hung.sneakery.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="transport")
public class Transport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="transport_name")
    private String name;

    @Column(name = "shipment")
    @Temporal(TemporalType.DATE)
    private Date shipment_date;


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

    public Date getShipment_date() {
        return shipment_date;
    }

    public void setShipment_date(Date shipment_date) {
        this.shipment_date = shipment_date;
    }
}
