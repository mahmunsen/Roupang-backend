package com.teamsupercat.roupangbackend.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.Instant;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Point_transaction", schema = "supercat")
@Builder
public class PointTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idx", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "member_idx", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY )
    @JoinColumn(name = "payments_idx")
    private Payment payment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_methods_idx")
    private PaymentMethod paymentMethod;

    @Column(name = "merchant_uid")
    private String merchantUid;

    @Column(name = "charge_point")
    private Long chargePoint;

    @Column(name = "used_point")
    private Long usedPoint;

    @Column(name = "transaction_amount")
    private Long transactionAmount;

    @Column(name = "remaining_points")
    private Long remainingPoints;

    @Column(name = "transaction_type", nullable = false, length = 50)
    private String transactionType;

    @CreationTimestamp
    @Column(name = "transaction_date", nullable = false)
    private Instant transactionDate;

}