package com.hung.sneakery.entity;

import lombok.*;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
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
public class Bid implements Serializable {
    //Using a Shared Primary Key
    @Id
    @Column(name = "product_id")
    private Long id;

    //@NotNull(message="The starting date is required")
    @Column(name = "bid_starting_date")
    //Use LocalDateTime over Timestamp
    //https://stackoverflow.com/questions/41998144/java8-localdatetime-or-timestamp
    private LocalDate bidStartingDate;

    @Column(name = "bid_closing_date")
    private LocalDateTime bidClosingDateTime;

    @NotNull(message = "The starting price is required")
    @Column(name = "price_start")
    private Long priceStart;

    @Column(name = "step_bid")
    private Long stepBid;

    @Column(name = "price_win")
    @Nullable
    private Long priceWin;

    //Using a Shared Primary Key - Behaves as a foreign key
    @OneToOne
    @MapsId
    @JoinColumn(name = "product_id")
    private Product product;

//    @ManyToMany(fetch = FetchType.EAGER)
//    @JoinTable(name= "bid_history",
//    joinColumns = @JoinColumn(name="product_id"),
//    inverseJoinColumns = @JoinColumn(name="buyer_id"))
//    private Set<User> users = new HashSet<>();

    @OneToMany(mappedBy = "bid")
    private Set<BidHistory> bidHistories = new HashSet<>();
}
