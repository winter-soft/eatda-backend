package com.proceed.swhackathon.controller;

import com.proceed.swhackathon.dto.orderDetail.OrderDetailDTO;
import com.proceed.swhackathon.dto.ResponseDTO;
import com.proceed.swhackathon.dto.orderDetail.OrderDetailInsertDTO;
import com.proceed.swhackathon.model.Order;
import com.proceed.swhackathon.model.UserOrderDetail;
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

    @ApiOperation(value = "메뉴 체크", notes = "true면 false, false면 true로 변경")
    @PutMapping("/{orderId}/{menuId}")
    public ResponseDTO<?> updateCart(@AuthenticationPrincipal String userId,
                                     @PathVariable Long orderId,
                                     @PathVariable Long menuId){
        return new ResponseDTO<>(HttpStatus.OK.value(),
                orderDetailService.updateMenuCheck(userId, orderId, menuId));
    }

    @ApiOperation(value = "주문 넣기", notes = "사용자의 장바구니에 담긴 메뉴를 order에 추가")
    @GetMapping("/userOrder/{orderId}")
    public ResponseDTO<?> addOrder(@AuthenticationPrincipal String userId,
                                   @PathVariable Long orderId){
        Long uodId = orderDetailService.addOrder(userId, orderId);
        return new ResponseDTO<>(HttpStatus.OK.value(),
                orderDetailService.detachUOD(userId, orderId, uodId));
    }

    @ApiOperation(value = "유저 주문목록 가져오기", notes = "")
    @GetMapping("/userOrderDetail/{orderId}")
    public ResponseDTO<?> selectUOD(@AuthenticationPrincipal String userId,
                                   @PathVariable Long orderId){
        return new ResponseDTO<>(HttpStatus.OK.value(),
                orderDetailService.selectUOD(userId, orderId));
    }

    @ApiOperation(value = "해당 유저의 모든 주문목록 가져오기", notes = "")
    @GetMapping("/userOrderDetail/")
    public ResponseDTO<?> selectUODAll(@AuthenticationPrincipal String userId){
        return new ResponseDTO<>(HttpStatus.OK.value(),
                orderDetailService.selectUODAll(userId));
    }
}
