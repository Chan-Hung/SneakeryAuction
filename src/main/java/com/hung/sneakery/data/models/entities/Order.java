package com.hung.sneakery.data.models.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Order implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "status")
    private String status;

    @OneToOne
    @JoinColumn(name="shipping_fee_id")
    private ShippingFee shippingFee;

    @OneToOne()
    @JoinColumn(name = "winner_id")
    private User winner;

    @OneToOne
    @JoinColumn(name = "seller_id")
    private User seller;

    @OneToOne()
    @JoinColumn(name="bid_id")
    private Bid bid;
}
