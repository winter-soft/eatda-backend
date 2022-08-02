package com.proceed.swhackathon.controller;

import com.proceed.swhackathon.dto.orderDetail.OrderDetailDTO;
import com.proceed.swhackathon.dto.ResponseDTO;
import com.proceed.swhackathon.dto.orderDetail.OrderDetailInsertDTO;
import com.proceed.swhackathon.service.OrderDetailService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orderDetail")
public class OrderDetailController {

    private final OrderDetailService orderDetailService;

    @ApiOperation(value = "장바구니 추가", notes = "")
    @PostMapping("/{orderId}/{menuId}")
    public ResponseDTO<?> insertOrderDetail(@AuthenticationPrincipal String userId,
                                            @PathVariable Long orderId,
                                            @PathVariable Long menuId,
                                            @RequestBody OrderDetailInsertDTO orderDetailDTO){
        return new ResponseDTO<>(HttpStatus.OK.value(),
                orderDetailService.insertOrderDetail(userId, orderId, menuId, orderDetailDTO));
    }

    @ApiOperation(value = "사용자별 장바구니 조회", notes = "")
    @GetMapping("/{orderId}")
    public ResponseDTO<?> selectCart(@AuthenticationPrincipal String userId,
                                     @PathVariable Long orderId){
        return new ResponseDTO<>(HttpStatus.OK.value(),
                orderDetailService.selectCart(userId, orderId));
    }
}
