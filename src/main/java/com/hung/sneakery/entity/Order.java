package com.hung.sneakery.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "status")
    private String status;

    @Column(name = "shipping_fee")
    private Long shippingFee;

    @Column(name = "subtotal")
    private Long subtotal;

    @OneToOne()
    @JoinColumn(name = "winner_id")
    private User winner;

    @OneToOne
    @JoinColumn(name = "seller_id")
    private User seller;

    @OneToOne()
    @JoinColumn(name = "bid_id")
    private Bid bid;
}
