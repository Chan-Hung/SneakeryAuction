package com.hung.sneakery.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
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
public class Bid {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @NotNull(message="The starting date is required")
    @Column(name = "bid_starting_date")
    //Use LocalDateTime over Timestamp
    //https://stackoverflow.com/questions/41998144/java8-localdatetime-or-timestamp
    private LocalDate bidStartingDate;

    @Column(name="bid_closing_date")
    private LocalDateTime bidClosingDateTime;

    @NotNull(message = "The starting price is required")
    @Column(name="price_start")
    private Long priceStart;

    @Column(name="step_bid")
    private Long stepBid;

    @Column(name="price_win")
    @Nullable
    private Long priceWin;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name= "bid_history",
    joinColumns = @JoinColumn(name="bid_id"),
    inverseJoinColumns = @JoinColumn(name="buyer_id"))
    private Set<User> users = new HashSet<>();
}
