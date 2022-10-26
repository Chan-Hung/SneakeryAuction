package com.hung.sneakery.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

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


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name= "bid_history",
    joinColumns = @JoinColumn(name="bid_id"),
    inverseJoinColumns = @JoinColumn(name="user_id"))
    private Set<User> users = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Timestamp getStart_bid() {
        return start_bid;
    }

    public void setStart_bid(Timestamp start_bid) {
        this.start_bid = start_bid;
    }

    public Timestamp getEnd_bid() {
        return end_bid;
    }

    public void setEnd_bid(Timestamp end_bid) {
        this.end_bid = end_bid;
    }

    public Long getPrice_start() {
        return price_start;
    }

    public void setPrice_start(Long price_start) {
        this.price_start = price_start;
    }

    public Long getStep_bid() {
        return step_bid;
    }

    public void setStep_bid(Long step_bid) {
        this.step_bid = step_bid;
    }

}
