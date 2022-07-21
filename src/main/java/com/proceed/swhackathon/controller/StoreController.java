package com.proceed.swhackathon.controller;

import com.proceed.swhackathon.dto.MenuDTO;
import com.proceed.swhackathon.dto.OrderDTO;
import com.proceed.swhackathon.dto.ResponseDTO;
import com.proceed.swhackathon.dto.StoreDTO;
import com.proceed.swhackathon.model.Menu;
import com.proceed.swhackathon.model.Store;
import com.proceed.swhackathon.repository.StoreRepository;
import com.proceed.swhackathon.service.StoreService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/store")
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;
    private final StoreRepository storeRepository;

    @ApiOperation(value = "가게상세", notes = "가게상세 페이지로 가게정보, 해당오더정보, 메뉴, 좋아요수를 리턴합니다.")
    @GetMapping("/storeDetail/{orderId}")
    public ResponseDTO<?> storeDetail(@AuthenticationPrincipal String userId, @PathVariable Long orderId){
        storeService.initialize();
        OrderDTO orderDTO = storeService.storeDetail(orderId);

        return new ResponseDTO<>(HttpStatus.OK.value(), orderDTO);
    }
}
