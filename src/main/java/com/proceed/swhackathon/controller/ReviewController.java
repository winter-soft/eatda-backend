package com.proceed.swhackathon.controller;

import com.proceed.swhackathon.dto.ResponseDTO;
import com.proceed.swhackathon.dto.review.ReviewRequestDTO;
import com.proceed.swhackathon.model.Review;
import com.proceed.swhackathon.repository.ReviewRepository;
import com.proceed.swhackathon.service.AwsS3Service;
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
import org.springframework.web.multipart.MultipartFile;

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

    @ApiOperation(value = "DTO를 받아 리뷰를 생성해주는 API",
            notes = "POST로 리뷰 작성 정보를 보내면 데이터베이스에 저장합니다.")
    @PostMapping("/{userOrderDetailId}")
    public ResponseDTO<?> insertReview(@ApiParam(name = "userOrderDetailId", value = "유저의 주문 정보를 PathVariable로 넘겨주세요.", example = "1") @PathVariable Long userOrderDetailId,
                                       @ApiParam(name = "ReviewRequestDTO", value = "star, content, MultiParFile을 form-data로 넘겨주세요.") @ModelAttribute ReviewRequestDTO requestDTO,
                                       @AuthenticationPrincipal String userId) {
        reviewService.insertReview(userOrderDetailId, userId, requestDTO);
        return new ResponseDTO<>(HttpStatus.OK.value(), "OK");
    }

    @ApiOperation(value = "reviewId를 받아 리뷰를 삭제해주는 API",
            notes = "visible을 false로 만듭니다. 자신의 리뷰만 삭제할 수 있다.")
    @DeleteMapping("/{reviewId}")
    public ResponseDTO<?> deleteReview(@ApiParam(name = "reviewId", value = "PathVariable로 넘겨주세요.") @PathVariable Long reviewId,
                                       @AuthenticationPrincipal String userId) {
        reviewService.deleteReview(reviewId, userId);
        return new ResponseDTO<>(HttpStatus.OK.value(), "OK");
    }

    @GetMapping("/select/{reviewId}")
    public ResponseDTO<?> selectByReviewId(@PathVariable Long reviewId) {

        return new ResponseDTO<>(HttpStatus.OK.value(),
                reviewService.findByReviewId(reviewId));
    }
}
