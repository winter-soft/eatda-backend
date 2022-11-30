package com.proceed.swhackathon.controller;

import com.proceed.swhackathon.dto.ResponseDTO;
import com.proceed.swhackathon.dto.payment.PaymentReqDTO;
import com.proceed.swhackathon.service.PaymentService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @Value("${payments.toss.clientKey}")
    private String clientKey;

    @PostMapping("/valid")
    @ApiOperation(value = "", notes = "couponUse_id가 없다면 0을 넘겨주세요.")
    public ResponseDTO<?> paymentValid(@AuthenticationPrincipal String userId,
                                       @RequestBody(required = true) PaymentReqDTO reqDTO){
        return paymentService.paymentValid(userId, reqDTO);
    }

    @GetMapping("/success")
    public ResponseDTO<?> paymentConfirm(@RequestParam String paymentKey,
                                         @RequestParam String orderId,
                                         @RequestParam Integer amount){
        return paymentService.paymentConfirm(clientKey, paymentKey, orderId, amount);
    }

    @GetMapping("/fail")
    public ResponseDTO<?> paymentCancel(@AuthenticationPrincipal String userId,
                                        @RequestParam Long userOrderDetailId){
        return paymentService.paymentCancel(clientKey, userId, userOrderDetailId);
    }
}
