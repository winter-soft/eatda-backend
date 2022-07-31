package com.proceed.swhackathon.controller;

import com.proceed.swhackathon.dto.OrderDetailDTO;
import com.proceed.swhackathon.dto.ResponseDTO;
import com.proceed.swhackathon.service.OrderDetailService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class OrderDetailController {

    private final OrderDetailService orderDetailService;

    @ApiOperation(value = "장바구니 추가", notes = "")
    @PostMapping("/storeDetail/{orderId}/{menuId}")
    public ResponseDTO<?> insertOrderDetail(@AuthenticationPrincipal String userId,
                                            @PathVariable Long orderId,
                                            @PathVariable Long menuId,
                                            @RequestBody OrderDetailDTO orderDetailDTO){
        return new ResponseDTO<>(HttpStatus.OK.value(),
                orderDetailService.insertOrderDeatil(userId, orderId, menuId, orderDetailDTO));
    }
}
