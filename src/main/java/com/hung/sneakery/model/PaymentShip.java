package com.hung.sneakery.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="payment_ship")
public class PaymentShip {

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

    public PaymentShip() {
    }

    public PaymentShip(Long id, String name, Long price, Date shipment_date, String provider, Order order) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.shipment_date = shipment_date;
        this.provider = provider;
        this.order = order;
    }

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

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Date getShipment_date() {
        return shipment_date;
    }

    public void setShipment_date(Date shipment_date) {
        this.shipment_date = shipment_date;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
