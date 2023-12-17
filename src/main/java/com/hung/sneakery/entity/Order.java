package com.hung.sneakery.entity;

import com.hung.sneakery.enums.EOrderStatus;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "orders")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order extends AbstractCommonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @Enumerated(EnumType.STRING)
    private EOrderStatus status;

    @Column
    private Long shippingFee;

    @Column
    private Long subtotal;

    @OneToOne
    @JoinColumn(name = "winner_id")
    private User winner;

    @OneToOne
    @JoinColumn(name = "seller_id")
    private User seller;

    @OneToOne
    @JoinColumn(name = "bid_id")
    private Bid bid;
}
