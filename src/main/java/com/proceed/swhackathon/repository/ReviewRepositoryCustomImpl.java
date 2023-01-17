package com.proceed.swhackathon.repository;

import com.proceed.swhackathon.dto.menu.MenuNameDTO;
import com.proceed.swhackathon.dto.review.ReviewResponseDTO;
import com.proceed.swhackathon.model.QReview;
import com.proceed.swhackathon.model.Review;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;

import java.util.Arrays;
import java.util.List;

import static com.proceed.swhackathon.model.QMenu.menu;
import static com.proceed.swhackathon.model.QOrder.order;
import static com.proceed.swhackathon.model.QOrderDetail.orderDetail;
import static com.proceed.swhackathon.model.QReview.*;
import static com.proceed.swhackathon.model.QUser.user;
import static com.proceed.swhackathon.model.QUserOrderDetail.userOrderDetail;
import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.group.GroupBy.list;

@RequiredArgsConstructor
public class ReviewRepositoryCustomImpl implements ReviewRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Review> findReviewAll() {
        return queryFactory
                .selectFrom(review)
                .fetch();
    }

    @Override
    public List<Double> reviewAvg() {

        List<Double> result = queryFactory
                .select(review.star.avg())
                .from(review)
                .join(review.userOrderDetail, userOrderDetail)
                .join(userOrderDetail.order, order)
                .groupBy(order.store)
                .fetch();
        return result;
    }

    /**
     * 1. storeId에 맞는 리뷰 페이지로 가져오기
     * 2. DTO에 맞게 변환
     * 3. 순서는 최신 리뷰 순으로
     *
     * @param storeId
     * @return Page<ReviewDTO>
     */
    @Override
    public List<ReviewResponseDTO> findByStoreId(Long storeId, PageRequest pageRequest) {

        return queryFactory
                .from(review)
                .join(review.userOrderDetail, userOrderDetail)
                .join(userOrderDetail.user, user)
                .join(userOrderDetail.orderDetails, orderDetail)
                .join(orderDetail.menu, menu)
                .join(userOrderDetail.order, order)
                .where(order.store.id.eq(storeId)
                        .and(review.visible.eq(false)))
                .offset(pageRequest.getOffset())
                .limit(pageRequest.getPageSize())
                .orderBy(review.id.desc())
                .transform(
                        groupBy(review.id).list( // Review id를 기준으로 그룹으로 묶어준다.
                                Projections.fields( // ReviewResponseDTO로 받기
                                        ReviewResponseDTO.class,
                                        review.id,
                                        review.imageUrl,
                                        review.star,
                                        review.content,
                                        user.username.as("createdBy"),
                                        list( // MenuNameDTO list로 변환해주는 과정
                                                Projections.fields(
                                                        MenuNameDTO.class,
                                                        menu.id,
                                                        menu.name
                                                )
                                        ).as("menuName"),
                                        review.createdTime.as("orderDate"),
                                        review.visible
                                )
                        )
                );
    }
}
