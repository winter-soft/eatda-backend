package com.proceed.swhackathon.service;

import com.proceed.swhackathon.dto.review.ReviewRequestDTO;
import com.proceed.swhackathon.dto.review.ReviewResponseDTO;
import com.proceed.swhackathon.exception.review.ReviewAlreadyExistsException;
import com.proceed.swhackathon.exception.review.ReviewNotFoundException;
import com.proceed.swhackathon.exception.userOrderDetail.UserOrderDetailNotFoundException;
import com.proceed.swhackathon.model.Review;
import com.proceed.swhackathon.model.UserOrderDetail;
import com.proceed.swhackathon.repository.ReviewRepository;
import com.proceed.swhackathon.repository.ReviewRepositoryCustomImpl;
import com.proceed.swhackathon.repository.UserOrderDetailRepository;
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

    private final AwsS3Service awsS3Service;
    private final ReviewRepository reviewRepository;
    private final UserOrderDetailRepository userOrderDetailRepository;

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

    // reviewRequestDTO를 통해 Review 생성
    @Transactional
    public void insertReview(Long userOrderDetailId, String userId, ReviewRequestDTO requestDTO) {
        // 해당 유저의 주문 목록이 맞는지 확인한다.
        UserOrderDetail uod = userOrderDetailRepository.findUODByIdAndUser(userOrderDetailId, userId).orElseThrow(() -> {
            throw new UserOrderDetailNotFoundException();
        });

        // 리뷰를 작성했는지 안했는지 확인
        if (uod.getUserOrderDetailStatus().equals("REVIEW_DONE")) {
            throw new ReviewAlreadyExistsException();
        } else if (uod.getUserOrderDetailStatus().equals("CANCEL")) {
            throw new UserOrderDetailNotFoundException();
        }

        String imageUrl = awsS3Service.uploadImage(requestDTO.getImage());
        Review review = Review.builder()
                .userOrderDetail(uod)
                .imageUrl(imageUrl)
                .star(requestDTO.getStar())
                .content(requestDTO.getContent())
                .visible(true)
                .build();
        reviewRepository.save(review);

        uod.setUserOrderDetailStatus("REVIEW_DONE");
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
