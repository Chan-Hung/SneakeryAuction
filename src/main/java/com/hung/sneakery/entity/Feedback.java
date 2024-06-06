package com.hung.sneakery.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "feedbacks")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Feedback extends AbstractCommonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int rating;

    @Column(nullable = false)
    private String feedbackText;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id", nullable = false)
    private User seller;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "winner_id", nullable = false)
    private User winner;
}
