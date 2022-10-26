package com.hung.sneakery.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Entity
@Table(name = "bids")
public class Bid {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message="The starting date is required")
    @Column(name = "start_bid")
    private Timestamp start_bid;

    @NotNull(message = "The ending date is required ")
    @Column (name = "end_bid")
    private Timestamp end_bid;

    @NotNull(message = "The starting price is required")
    @Column(name="price_start")
    private Long price_start;

    @Column(name="step_bid")
    private Long step_bid;



    //ManyToMany bid_user

}
