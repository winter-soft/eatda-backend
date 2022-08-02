package com.proceed.swhackathon.controller;

import com.proceed.swhackathon.dto.order.OrderInsertDTO;
import com.proceed.swhackathon.dto.order.OrderStatusDTO;
import com.proceed.swhackathon.dto.ResponseDTO;
import com.proceed.swhackathon.service.OrderService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @ApiOperation(value = "가게 주문 생성", notes = "가게의 Order주문을 오픈한다.")
    @PostMapping("/{storeId}")
    public ResponseDTO<?> insert(@RequestBody OrderInsertDTO orderDTO,
                                 @PathVariable Long storeId){
        return new ResponseDTO<>(HttpStatus.OK.value(), orderService.insert(orderDTO, storeId));
    }

    @ApiOperation(value = "가게 주문 상태 변경", notes = "가게 주문상태를 변경한다.(사장만 가능)")
    @PutMapping("/{orderId}")
    public ResponseDTO<?> updateStatus(@AuthenticationPrincipal String userId,
                                       @RequestBody OrderStatusDTO orderStatus,
                                       @PathVariable Long orderId){
        return new ResponseDTO<>(HttpStatus.OK.value(),
                orderService.updateStatus(userId, orderId, orderStatus.getOrderStatus()));
    }

    @ApiOperation(value = "가게주문 한건 조회", notes = "")
    @GetMapping("/{orderId}")
    public ResponseDTO<?> select(@PathVariable Long orderId){
        return new ResponseDTO<>(HttpStatus.OK.value(), orderService.select(orderId));
    }

    @ApiOperation(value = "가게주문 조회", notes = "")
    @GetMapping("/")
    public ResponseDTO<?> selectAll(@PageableDefault(sort = "currentAmount",direction = Sort.Direction.DESC)
                                    Pageable pageable){
        return new ResponseDTO<>(HttpStatus.OK.value(), orderService.selectAll(pageable));
    }
}
