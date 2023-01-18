package com.proceed.swhackathon.repository;

import com.proceed.swhackathon.dto.review.ReviewResponseDTO;
import com.proceed.swhackathon.model.Review;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

public interface ReviewRepositoryCustom {
    public List<Review> findReviewAll();
    public List<Double> reviewAvg();
    public List<ReviewResponseDTO> findByStoreId(Long storeId, PageRequest pageRequest);
    public Optional<Review> validReviewWriter(Long reviewId, String userId);
    public List<ReviewResponseDTO> findReviewById(Long reviewId);
}
