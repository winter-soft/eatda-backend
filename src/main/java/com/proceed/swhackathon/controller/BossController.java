package com.proceed.swhackathon.controller;

import com.proceed.swhackathon.dto.ResponseDTO;
import com.proceed.swhackathon.service.BossService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/boss")
@RequiredArgsConstructor
public class BossController {
    private final BossService bossService;

    @GetMapping("/{orderId}")
    @ApiOperation(value = "해당 딜의 UserOrderDetail를 리턴해준다.", notes = "")
    public ResponseDTO<?> selectBossOrder(@AuthenticationPrincipal String userId,
                                          @PathVariable Long orderId){
        return bossService.selectBossOrder(userId, orderId);
    }

    @GetMapping("/dateOrder")
    @ApiOperation(value = "년도와 날짜를 넣어주면 해당 날짜에 맞는 order들을 호출해준다.", notes = "")
    public ResponseDTO<?> selectDateOrder(@AuthenticationPrincipal String userId,
                                          @RequestParam String date) {
        return bossService.selectDateOrder(userId, date);
    }
}
