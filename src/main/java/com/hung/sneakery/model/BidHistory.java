package com.hung.sneakery.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "bid_history")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

//This extra tables have 2 more properties: price and createAt
public class BidHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="price")
    private Long price;

    @Column(name="created_at")
    private LocalDateTime createdAt;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="product_id")
    @JsonIgnore
    private Bid bid;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="buyer_id")
    @JsonIgnore
    private User user;
}
