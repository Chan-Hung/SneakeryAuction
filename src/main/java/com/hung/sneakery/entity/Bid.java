package com.hung.sneakery.entity;

import com.hung.sneakery.enums.BidOutcome;
import com.hung.sneakery.enums.PaymentStatus;
import lombok.*;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "bids")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Bid extends AbstractCommonEntity {

    @Id
    @Column(name = "product_id")
    private Long id;

    @Column(name = "bid_closing_date")
    private LocalDateTime closingDateTime;

    @NotNull(message = "The starting price is required")
    @Column
    private Long priceStart;

    @Column
    private Long stepBid;

    @Column
    @Nullable
    private Long priceWin;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "holder_id")
    private User holder;

    @Column
    @Nullable
    private Long reservePrice;

    @Column
    private Boolean isBidSnipping;

    @Column
    @Enumerated(EnumType.STRING)
    private BidOutcome bidOutcome = BidOutcome.OPEN;

    @Column
    @Enumerated(EnumType.STRING)
    private PaymentStatus sellerPaymentStatus;

    @Column
    @Enumerated(EnumType.STRING)
    private PaymentStatus winnerPaymentStatus;

    //Using a Shared Primary Key - Behaves as a foreign key
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "product_id")
    private Product product;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "bid", cascade = CascadeType.ALL)
    private Set<BidHistory> bidHistories = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "bid", cascade = CascadeType.ALL)
    private Set<Transaction> transactions;
}
