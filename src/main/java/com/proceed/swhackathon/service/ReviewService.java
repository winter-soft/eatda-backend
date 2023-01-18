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

        return ReviewResponseDTO.of(reviewRepository.findByStoreId(storeId, pageRequest));
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

    // reviewId를 통해 review의 visible 컬럼을 false로 만듬으로써 삭제 기능을 대신한다.
    @Transactional
    public void deleteReview(Long reviewId, String userId) {
        // 해당 유저의 리뷰인지 검증한다.
        Review review = reviewRepository.validReviewWriter(reviewId, userId).orElseThrow(() -> {
            throw new ReviewNotFoundException();
        });

        review.setVisible(false); // 리뷰 숨김처리
    }

    // 리뷰 한 건 조회하기
    public ReviewResponseDTO findByReviewId(Long reviewId) {
        List<ReviewResponseDTO> review = reviewRepository.findReviewById(reviewId);

        if(review.size() == 0) {
            throw new ReviewNotFoundException();
        }

        return ReviewResponseDTO.of(review).get(0); // 리뷰는 한개 이므로 get(0)
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
