package com.proceed.swhackathon.repository;

import com.proceed.swhackathon.dto.ResponseDTO;
import com.proceed.swhackathon.dto.menu.MenuNameDTO;
import com.proceed.swhackathon.dto.review.ReviewResponseDTO;
import com.proceed.swhackathon.model.QReview;
import com.proceed.swhackathon.model.Review;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.proceed.swhackathon.model.QMenu.menu;
import static com.proceed.swhackathon.model.QOrder.order;
import static com.proceed.swhackathon.model.QOrderDetail.orderDetail;
import static com.proceed.swhackathon.model.QReview.review;
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
    public List<Tuple> reviewAvg() {

        return queryFactory
                .select(
                        order.store.id,
                        review.star.avg())
                .from(review)
                .join(review.userOrderDetail, userOrderDetail)
                .join(userOrderDetail.order, order)
                .groupBy(order.store)
                .fetch();
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
                        .and(review.visible.eq(true)))
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

    /**
     * 1. reviewId를 통해 리뷰 가져오기
     * 2. userId를 통한 해당 유저의 리뷰가 맞는지 검증
     * 3. visible이 true인지 검증
     * 4. 예외 처리를 위해 Optional로 감싸기
     *
     * @param reviewId
     * @param userId
     * @return
     */
    @Override
    public Optional<Review> validReviewWriter(Long reviewId, String userId) {
        Review result = queryFactory
                .selectFrom(review)
                .where(
                        review.id.eq(reviewId)
                                .and(review.createdBy.eq(userId))
                                .and(review.visible.eq(true)))
                .fetchOne();

        return Optional.ofNullable(result); // Optional 객체로 변환 시켜서 리턴
    }

    /**
     * 1. reviewId를 받아서 리뷰를 한 건 조회한다.
     *
     * @param reviewId
     * @return
     */
    @Override
    public List<ReviewResponseDTO> findReviewById(Long reviewId) {

        return queryFactory
                .from(review)
                .join(review.userOrderDetail, userOrderDetail)
                .join(userOrderDetail.orderDetails, orderDetail)
                .join(orderDetail.menu, menu)
                .join(user).on(review.createdBy.eq(user.id))
                .where(
                        review.id.eq(reviewId)
                        .and(review.visible.eq(true)))
                .transform(
                        groupBy(review.id).list(
                                Projections.fields(
                                        ReviewResponseDTO.class,
                                        review.id,
                                        review.imageUrl,
                                        review.star,
                                        review.content,
                                        user.username.as("createdBy"),
                                        list(
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
