package com.proceed.swhackathon.controller;

import com.proceed.swhackathon.dto.ResponseDTO;
import com.proceed.swhackathon.dto.menu.MenuDTO;
import com.proceed.swhackathon.dto.menu.MenuInsertDTO;
import com.proceed.swhackathon.service.MenuService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/menu")
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;

    @ApiOperation(value = "가게의 메뉴를 추가한다.", notes = "그 가게의 사장만 가능하다.")
    @PostMapping("/{storeId}/insert")
    public ResponseDTO<?> addMenu(@AuthenticationPrincipal String userId,
                                  @PathVariable Long storeId,
                                  @RequestBody MenuInsertDTO menuDTO){
        return new ResponseDTO<>(HttpStatus.OK.value(), menuService.addMenu(userId, storeId, menuDTO));
    }
}
