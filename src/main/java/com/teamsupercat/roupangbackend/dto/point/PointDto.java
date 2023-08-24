package com.teamsupercat.roupangbackend.dto.point;


import com.teamsupercat.roupangbackend.entity.PointTransaction;
import lombok.*;

import java.time.Instant;

@Setter
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PointDto {

    private Integer memberIdx;
    private String email;
    private Integer paymentIdx;
    private Integer paymentMethodIdx;
    private String transactionType;
    private Long transactionAmount;
    private Instant transactionDate;
    private Long remainingPoints;
    private Long chargePoint;
    private Long userPoint;

    public static PointDto fromEntity(PointTransaction pointTransaction){
        return PointDto.builder()
                .memberIdx(pointTransaction.getMember().getId())
                .email(pointTransaction.getMember().getEmail())
                .paymentIdx(pointTransaction.getPayment()!=null ? pointTransaction.getPayment().getId() : 0)
                .paymentMethodIdx(pointTransaction.getPaymentMethod()!=null ? pointTransaction.getPaymentMethod().getId() : 0)
                .transactionAmount(pointTransaction.getTransactionAmount())
                .transactionDate(pointTransaction.getTransactionDate())
                .remainingPoints(pointTransaction.getRemainingPoints())
                .chargePoint(pointTransaction.getChargePoint())
                .userPoint(pointTransaction.getChargePoint() + pointTransaction.getMember().getUserPoint())
                .build();
    }


}
