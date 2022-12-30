package com.proceed.swhackathon.controller;

import com.proceed.swhackathon.dto.ResponseDTO;
import com.proceed.swhackathon.model.Review;
import com.proceed.swhackathon.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/review")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewRepository reviewRepository;

    @GetMapping("/")
    public ResponseDTO<?> createdByTest(@AuthenticationPrincipal String userId) {
        Review review = testClass();

        Review result = reviewRepository.save(review);
        return new ResponseDTO<>(HttpStatus.OK.value(), result.getCreatedBy());
    }

    public Review testClass() {
        return Review.builder()
                .imageUrl(null)
                .star(5)
                .content("맛있네요")
                .build();
    }
}
