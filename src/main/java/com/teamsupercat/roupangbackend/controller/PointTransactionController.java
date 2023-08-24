package com.teamsupercat.roupangbackend.controller;

import com.teamsupercat.roupangbackend.common.ResponseDto;
import com.teamsupercat.roupangbackend.dto.CustomUserDetail.CustomUserDetail;
import com.teamsupercat.roupangbackend.dto.point.*;
import com.teamsupercat.roupangbackend.service.PointTransactionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Api(tags = "포인트충전 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/point")
public class PointTransactionController {

    private final PointTransactionService pointTransactionService;
//
//    @ApiOperation(value = "토큰가져오기")
//    @PostMapping("/accesstoken")
//    public ResponseEntity<AccessTokenResponse> getAccessToken(
//            @AuthenticationPrincipal UserDetails userDetails,
//            @RequestBody @Valid IampartDto iampartDto
//    ) {
//
//        String email = userDetails.getUsername();
//
//        AccessTokenResponse accessTokenResponse = pointTransactionService.getAccessToken(email,iampartDto.getApiKey(),iampartDto.getApiSecret());
//        return ResponseEntity.ok(accessTokenResponse);
//    }
//
//    @ApiOperation(value = "포인트 충전 사전검증")
//    @PostMapping("/prepare")
//    public ResponseEntity<String> pointPrepare(@RequestBody @Valid PointPrepareRequest request) {
//        return pointTransactionService.pointPrepare(request.getMerchantUid(), request.getAmount());
//    }

    // prepare (사전검증) : 주문번호 생성해야함
    // 생성한 주문번호로 사전검증 완료되면 프론트로도 응답해주고
    // 프론트에서 결제완료 처리되면

    /*
    url: "https://api.iamport.kr/payments/prepare",
    method: "post",
    headers: { "Content-Type": "application/json" },
    data: {
    merchant_uid: "...", // 가맹점 주문번호
    amount: 420000, // 결제 예정금액
    */

    // merchant_uid:  내가 생성한 주문번호를 프론트에서 돌려받음
    // merchant_uid을 돌려받으면
    // https://developers.portone.io/docs/ko/auth/guide/5/post (사후검증 docs url)

    // complete (사후검증)
    // 응답받은 내용을 바탕으로 실 결제 금액과 결제요청금액(가맹점 자체 데이터베이스)을 비교
    // 결제 상세내역 조회를 위해 포트원 결제 단건 조회 API 요청
    // 포트원 결제고유번호(imp_uid), 가맹점 주문번호(merchant_uid)를 프론트엔드로부터 수신

    // 사후검증 완료 되면 결제완료 처리 해주기.

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