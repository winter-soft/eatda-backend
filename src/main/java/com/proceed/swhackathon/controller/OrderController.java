package com.proceed.swhackathon.controller;

import com.proceed.swhackathon.dto.OrderDTO;
import com.proceed.swhackathon.dto.ResponseDTO;
import com.proceed.swhackathon.model.OrderStatus;
import com.proceed.swhackathon.service.OrderService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @ApiOperation(value = "가게 주문 생성", notes = "가게의 Order주문을 오픈한다.")
    @PostMapping("/insert/{storeId}")
    public ResponseDTO<?> insert(@RequestBody OrderDTO orderDTO, @PathVariable Long storeId){
        return new ResponseDTO<>(HttpStatus.OK.value(), orderService.insert(orderDTO, storeId));
    }


}
