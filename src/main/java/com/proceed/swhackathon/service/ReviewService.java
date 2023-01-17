package com.proceed.swhackathon.service;

import com.proceed.swhackathon.dto.review.ReviewResponseDTO;
import com.proceed.swhackathon.exception.review.ReviewNotFoundException;
import com.proceed.swhackathon.model.Review;
import com.proceed.swhackathon.repository.ReviewRepository;
import com.proceed.swhackathon.repository.ReviewRepositoryCustomImpl;
import com.proceed.swhackathon.utils.DateDistance;
import com.proceed.swhackathon.utils.UserNickName;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    // storeId를 통해 리뷰를 조회
    public List<ReviewResponseDTO> findByStoreId(Long storeId, int page) {
        PageRequest pageRequest = PageRequest.of(page, 5);
        return reviewRepository.findByStoreId(storeId, pageRequest)
                .stream()
                .map((x) -> {
                    x.setRelativeDate(DateDistance.of(x.getOrderDate())); // 상대적인 날짜 계산
                    x.setCreatedBy(UserNickName.of(x.getCreatedBy())); // 닉네임으로 변경

                    return x;
                }).collect(Collectors.toList());
    }

    // 리뷰 평균 계산 크론탭
//    @Scheduled(cron = "0 0 0/1 * * *") // 매 시간마다 실행
//    public void reviewGradeUpdate() {
//        List<Double> results = reviewRepository.reviewAvg();
//
//        for(Double reuslt : results) {
//            System.out.println("reuslt = " + reuslt);
//        }
//    }
}
