package com.teamsupercat.roupangbackend.service;

import com.teamsupercat.roupangbackend.common.CustomException;
import com.teamsupercat.roupangbackend.common.ErrorCode;
import com.teamsupercat.roupangbackend.dto.point.AccessTokenResponse;
import com.teamsupercat.roupangbackend.dto.point.ChargePoint;
import com.teamsupercat.roupangbackend.dto.point.PointDto;
import com.teamsupercat.roupangbackend.entity.Member;
import com.teamsupercat.roupangbackend.entity.PointTransaction;
import com.teamsupercat.roupangbackend.repository.MemberRepository;
import com.teamsupercat.roupangbackend.repository.PointTransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
@Slf4j
public class PointTransactionService {

    private final MemberRepository memberRepository;
    private final PointTransactionRepository pointTransactionRepository;
//    private final RestTemplate restTemplate;
//
//    private static final String API_BASE_URL = "https://api.iamport.kr";
//
//    // API 키와 시크릿을 클래스 필드로 선언
//    private static final String API_KEY = "YOUR_API_KEY";
//    private static final String API_SECRET = "YOUR_API_SECRET";


//    @Transactional
//    public AccessTokenResponse getAccessToken(String email, String apiKey, String apiSecret) {
//
//        Member member = memberRepository.findByEmail(email)
//                .orElseThrow(()-> new CustomException(ErrorCode.IM_PORT_API_COMMUNICATION_ERROR));
//
//        log.info("{} member",member);
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
//        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
//        params.add("imp_key", apiKey);
//        params.add("imp_secret", apiSecret);
//
//
//        log.info("{} apiKey", apiKey);
//        log.info("{} apiSecret", apiSecret);
//
//        HttpEntity<?> entity = new HttpEntity<>(params, headers);
//        log.info("{} apiKey", apiKey);
//        log.info("{} apiSecret", apiSecret);
//        ResponseEntity<AccessTokenResponse> response = restTemplate.exchange(
//                API_BASE_URL + "/users/getToken", HttpMethod.POST, entity, AccessTokenResponse.class
//        );
//        log.info("{} response",response);
//        // 응답이 200이면 성공 && 응답의 본문이 null이 아니면 응답 본문에 데이터가 포함
//        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
//            return response.getBody();
//        } else {
//            throw new CustomException(ErrorCode.IM_PORT_API_COMMUNICATION_ERROR);
//        }
//
//    }


//    public ResponseEntity<String> pointPrepare(String merchantUid, int amount) {
//        // merchantUid와 amount를 사용하여 사전검증 로직 수행
//        // 여기에 사전검증 로직을 작성하고, 결과에 따라 적절한 응답을 생성
//        // 예를 들어, 검증 성공 시 "Payment prepared successfully" 메시지를 반환하도록 구현
//
//        // merchantUid가 유효한지 확인
//        if (!isMerchantUidValid(merchantUid)) {
//            return ResponseEntity.badRequest().body("Invalid merchantUid");
//        }
//
//        // amount가 0보다 큰지 확인
//        if (amount <= 0) {
//            return ResponseEntity.badRequest().body("Invalid amount");
//        }
//
//        // 사전검증 성공
//        return ResponseEntity.ok("Payment prepared successfully");
//    }
//
//    private boolean isMerchantUidValid(String merchantUid) {
//        // merchantUid 유효성 검사 로직을 구현
//        return merchantUid.matches("^[a-zA-Z0-9-]+$");
//    }


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

    // getUserPointsHistory


