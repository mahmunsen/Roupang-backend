package com.teamsupercat.roupangbackend.service;

import com.teamsupercat.roupangbackend.common.CustomException;
import com.teamsupercat.roupangbackend.common.ErrorCode;
import com.teamsupercat.roupangbackend.dto.point.ChargePoint;
import com.teamsupercat.roupangbackend.dto.point.PointDto;
import com.teamsupercat.roupangbackend.entity.Member;
import com.teamsupercat.roupangbackend.entity.PointTransaction;
import com.teamsupercat.roupangbackend.repository.MemberRepository;
import com.teamsupercat.roupangbackend.repository.PointTransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PointTransactionService {

    private final MemberRepository memberRepository;
    private final PointTransactionRepository pointTransactionRepository;

    @Transactional
    public PointDto chargePoints(
            String email,
            ChargePoint.Request request){

        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.MY_PAGE_PERMISSION_DENIED));




        PointTransaction pointTransaction = PointTransaction.builder()
                .member(member)
                .transactionType("충전")
                .transactionAmount(request.getTransactionAmount())
                .chargePoint(request.getChargePoint())
                .build();

        PointDto pointDto = PointDto.fromEntity(
                pointTransactionRepository.save(pointTransaction)
        );

        member.setUserPoint(pointDto.getUserPoint());

        return pointDto;

        }

    }


