package com.hung.sneakery.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "bid_win")
public class BidWin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //private Product product;

    @NotNull(message="The winner price is required")
    @Column(name = "price_win")
    private Long price_win;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="user_id", referencedColumnName = "id")
    private User user;
}
