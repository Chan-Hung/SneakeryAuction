package com.hung.sneakery.model;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "bid_history")
public class BidHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="price")
    private Long price;

    @Column(name="created_at")
    private Timestamp createdAt;




}
