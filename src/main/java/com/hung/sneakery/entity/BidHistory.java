package com.hung.sneakery.entity;

import com.hung.sneakery.enums.EBidStatus;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "bid_history")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BidHistory extends AbstractCommonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long maxPrice;

    @Column
    private Long actualPrice;

    @Column
    @Enumerated(EnumType.STRING)
    private EBidStatus status;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id")
    private Bid bid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "buyer_id")
    private User user;
}
