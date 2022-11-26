package com.proceed.swhackathon.controller;

import com.proceed.swhackathon.dto.ResponseDTO;
import com.proceed.swhackathon.dto.menu.MenuDTO;
import com.proceed.swhackathon.dto.menu.MenuInsertDTO;
import com.proceed.swhackathon.dto.menu.MenuUpdateDTO;
import com.proceed.swhackathon.service.AwsS3Service;
import com.proceed.swhackathon.service.MenuService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/menu")
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;
    private final AwsS3Service s3Service;

    @ApiOperation(value = "가게의 메뉴 정보 1건 조회", notes = "")
    @GetMapping("/{menuId}")
    public ResponseDTO<?> selectMenu(@PathVariable Long menuId){
        return menuService.selectMenu(menuId);
    }

    @ApiOperation(value = "가게의 메뉴를 추가한다.", notes = "그 가게의 사장만 가능하다.")
    @PostMapping("/")
    public ResponseDTO<?> insertMenu(@AuthenticationPrincipal String userId,
                                     MenuInsertDTO menuDTO,
                                     @RequestPart("file") MultipartFile multipartFile){
        String imgPath = s3Service.upload(multipartFile);
        menuDTO.setImageUrl(imgPath);
        return new ResponseDTO<>(HttpStatus.OK.value(), menuService.addMenu(userId, menuDTO));
    }

    @ApiOperation(value = "가게의 메뉴를 수정한다.", notes = "가게의 사장만 가능하다.")
    @PutMapping("/{menuId}")
    public ResponseDTO<?> updateMenu(@AuthenticationPrincipal String userId,
                                     @PathVariable Long menuId,
                                     @RequestBody MenuUpdateDTO menuDTO){
        return new ResponseDTO<>(HttpStatus.OK.value(), menuService.updateMenu(userId, menuId, menuDTO));
    }

    @ApiOperation(value = "메뉴를 삭제한다.", notes = "가게의 사장만 가능하다.")
    @DeleteMapping("/{menuId}")
    public ResponseDTO<?> deleteMenu(@AuthenticationPrincipal String userId,
                                     @PathVariable Long menuId){
        return new ResponseDTO<>(HttpStatus.OK.value(), menuService.deleteMenu(userId, menuId));
    }
}
