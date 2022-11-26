package com.proceed.swhackathon.controller;

import com.proceed.swhackathon.dto.ResponseDTO;
import com.proceed.swhackathon.repository.MenuOptionRepository;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/menuOption")
@RequiredArgsConstructor
public class MenuOptionController {
    private final MenuOptionRepository menuOptionRepository;

//    @GetMapping("/{menuId}")
//    @ApiOperation(value = "메뉴에 맞는 옵션값을 반환한다.", notes = "menuId를 받아 옵션값을 리턴")
//    public ResponseDTO<?> selectMenuOption(@PathVariable Long menuId){
//        Map<String, Object> m = new HashMap<>();
//        m.put("menuOptions", menuOptionRepository.findAllByMenu_id(menuId));
//
//        return new ResponseDTO<>(HttpStatus.OK.value(), m);
//    }
}
