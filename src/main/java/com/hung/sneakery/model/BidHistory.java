package com.hung.sneakery.model;

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
}
