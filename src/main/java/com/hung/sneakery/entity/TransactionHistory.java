package com.hung.sneakery.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hung.sneakery.enums.EPaymentStatus;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "transactions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionHistory extends AbstractCommonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long amount;

    @Column
    @Enumerated(EnumType.STRING)
    private EPaymentStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wallet_id")
    @JsonIgnore
    private Wallet wallet;
}
