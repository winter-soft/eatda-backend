package com.proceed.swhackathon.controller;

import com.proceed.swhackathon.dto.menuOption.MenuOptionDTO;
import com.proceed.swhackathon.dto.orderDetail.OrderDetailDTO;
import com.proceed.swhackathon.dto.ResponseDTO;
import com.proceed.swhackathon.dto.orderDetail.OrderDetailInsertDTO;
import com.proceed.swhackathon.dto.orderDetail.OrderDetailQuantityDTO;
import com.proceed.swhackathon.model.Order;
import com.proceed.swhackathon.model.UserOrderDetail;
import com.proceed.swhackathon.service.OrderDetailService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
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
    @GetMapping("/")
    public ResponseDTO<?> selectCart(@AuthenticationPrincipal String userId){
        return new ResponseDTO<>(HttpStatus.OK.value(),
                orderDetailService.selectCart(userId));
    }

    @ApiOperation(value = "메뉴 체크", notes = "true면 false, false면 true로 변경")
    @PutMapping("/{orderDetailId}")
    public ResponseDTO<?> updateCart(@AuthenticationPrincipal String userId,
                                     @PathVariable Long orderDetailId){
        return new ResponseDTO<>(HttpStatus.OK.value(),
                orderDetailService.updateMenuCheck(userId, orderDetailId));
    }

    @ApiOperation(value = "수량 변경", notes = "quantity를 RequestBody로 보내주세요.")
    @PutMapping("/quantity/{orderDetailId}")
    public ResponseDTO<?> updateQuantity(@AuthenticationPrincipal String userId,
                                         @PathVariable Long orderDetailId,
                                         @RequestBody OrderDetailQuantityDTO quantityDTO){
        return new ResponseDTO<>(HttpStatus.OK.value(),
                orderDetailService.updateQuantity(userId, orderDetailId, quantityDTO.getQuantity()));
    }

    @ApiOperation(value = "주문 넣기", notes = "사용자의 장바구니에 담긴 메뉴를 order에 추가")
    @GetMapping("/userOrder/{orderId}")
    public ResponseDTO<?> addOrder(@AuthenticationPrincipal String userId,
                                   @PathVariable Long orderId,
                                   @RequestParam(defaultValue = "0") String couponUseId,
                                   @RequestParam(defaultValue = "0") String paymentId){
        Long uodId = orderDetailService.addOrder(userId, orderId, paymentId);
        return new ResponseDTO<>(HttpStatus.OK.value(),
                orderDetailService.detachUOD(userId, orderId, uodId, Long.parseLong(couponUseId)));
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
