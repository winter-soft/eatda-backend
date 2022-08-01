package com.proceed.swhackathon.controller;

import com.proceed.swhackathon.dto.ResponseDTO;
import com.proceed.swhackathon.service.LikesService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/likes")
@RequiredArgsConstructor
public class LikesController {

    private final LikesService likesService;

    @ApiOperation(value = "사용자,가게별 좋아요", notes = "좋아요를 이미 했으면 삭제, 아니라면 추가")
    @GetMapping("/{storeId}")
    public ResponseDTO<?> clickLikes(@AuthenticationPrincipal String userId,
                                     @PathVariable Long storeId) {
        return new ResponseDTO<>(HttpStatus.OK.value(), likesService.clickLikes(userId, storeId));
    }

    @ApiOperation(value = "사용자 좋아요 목록", notes = "")
    @GetMapping("/")
    public ResponseDTO<?> likesList(@AuthenticationPrincipal String userId) {
        return new ResponseDTO<>(HttpStatus.OK.value(), likesService.likesList(userId));
    }
}
