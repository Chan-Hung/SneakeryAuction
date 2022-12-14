package com.hung.sneakery.data.models.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name= "wallets")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Wallet implements Serializable {
    @Id
    @Column(name="user_id")
    private Long id;

    @Column(name = "wallet_balance")
    private Long balance;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;
}
