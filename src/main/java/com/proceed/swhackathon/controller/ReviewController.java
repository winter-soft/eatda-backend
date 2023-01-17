package com.proceed.swhackathon.controller;

import com.proceed.swhackathon.dto.ResponseDTO;
import com.proceed.swhackathon.model.Review;
import com.proceed.swhackathon.repository.ReviewRepository;
import com.proceed.swhackathon.service.ReviewService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/review")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @ApiOperation(value = "storeId를 받아 리뷰를 5개씩 내려주는 API",
            notes = "GET Parameter로 page를 넘겨주면 해당 페이지의 댓글을 가져옵니다.")
    @GetMapping("/{storeId}")
    public ResponseDTO<?> selectByStoreId(@ApiParam(name = "storeId", value = "PathVariable로 넘겨주세요.", example = "1") @PathVariable Long storeId,
                                          @ApiParam(name = "page", value = "RequestParam으로 넘겨주세요.", example = "23")@RequestParam(defaultValue = "0") Integer page) {

        return new ResponseDTO<>(HttpStatus.OK.value(),
                reviewService.findByStoreId(storeId, page));
    }
}
