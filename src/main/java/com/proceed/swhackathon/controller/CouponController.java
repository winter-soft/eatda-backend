package com.proceed.swhackathon.controller;

import com.proceed.swhackathon.dto.ResponseDTO;
import com.proceed.swhackathon.dto.coupon.CouponReqDTO;
import com.proceed.swhackathon.service.CouponService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/coupon")
@RequiredArgsConstructor
public class CouponController {
    private final CouponService couponService;

    @PostMapping("/")
    @ApiOperation(value = "쿠폰등록하기", notes = "couponCode를 보내주세요.")
    public ResponseDTO<?> registerCoupon(@AuthenticationPrincipal String userId,
                                         @RequestBody(required = true)CouponReqDTO couponReqDTO){
        return couponService.registerCoupon(userId, couponReqDTO.getCouponCode());
    }

    @GetMapping("/")
    @ApiOperation(value = "쿠폰 목록 조회하기", notes = "해당 유저의 사용하지 않은 쿠폰목록을 조회한다.")
    public ResponseDTO<?> selectUserCoupon(@AuthenticationPrincipal String userId) {
        return couponService.selectUserCoupon(userId);
    }
}
