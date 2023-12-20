package com.hung.sneakery.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private Long price;

    @Column
    @Enumerated(EnumType.STRING)
    private EBidStatus status;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id")
    @JsonIgnore
    private Bid bid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "buyer_id")
    @JsonIgnore
    private User user;
}
