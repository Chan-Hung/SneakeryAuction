package com.hung.sneakery.data.models.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="payment_ship")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentShip implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="payment_name")
    private String name;

    @Column(name = "price_ship")
    private Long price;

    @Column(name = "shipment_date")
    private Date shipment_date;

    @Column(name = "provider")
    private String provider;

    @OneToOne
    @JoinColumn(name = "order_id")
    private Order order;
}
