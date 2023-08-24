package com.teamsupercat.roupangbackend.controller;

import com.teamsupercat.roupangbackend.common.ResponseDto;
import com.teamsupercat.roupangbackend.dto.CustomUserDetail.CustomUserDetail;
import com.teamsupercat.roupangbackend.dto.point.ChargePoint;
import com.teamsupercat.roupangbackend.service.PointTransactionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "포인트충전 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/point")
public class PointTransactionController {

    private final PointTransactionService pointTransactionService;


    @ApiOperation(value = "포인트 충전")
    @PostMapping("/charge")
    public ResponseDto<ChargePoint.Response> charge(
            @AuthenticationPrincipal CustomUserDetail userDetails,
            @RequestBody ChargePoint.Request request) {

        String email = userDetails.getUsername();

        return ResponseDto.success(ChargePoint.Response.from(
                pointTransactionService.chargePoints(email,request)
        ));
    }
}