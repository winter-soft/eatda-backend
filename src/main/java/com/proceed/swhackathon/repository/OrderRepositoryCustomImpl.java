package com.proceed.swhackathon.repository;

import com.proceed.swhackathon.dto.order.OrderDTO;
import com.proceed.swhackathon.dto.store.StoreInOrderDTO;
import com.proceed.swhackathon.dto.user.UserInfoDTO;
import com.proceed.swhackathon.model.OrderStatus;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.proceed.swhackathon.model.QDestination.destination;
import static com.proceed.swhackathon.model.QOrder.order;
import static com.proceed.swhackathon.model.QStore.store;
import static com.proceed.swhackathon.model.QUser.user;
import static com.proceed.swhackathon.model.QUserOrderDetail.userOrderDetail;
import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.group.GroupBy.list;

@RequiredArgsConstructor
public class OrderRepositoryCustomImpl implements OrderRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<OrderDTO> findOrder() {

        return queryFactory
                .from(order)
                .join(order.destination, destination)
                .join(order.userOrderDetails, userOrderDetail)
                .join(userOrderDetail.user, user)
                .join(order.store, store)
                .orderBy(order.updatedTime.desc())
                .transform(
                        groupBy(order.id).list(
                                Projections.fields(
                                        OrderDTO.class,
                                        order.id,
                                        order.orderStatus,
                                        order.currentAmount,
                                        order.startTime,
                                        order.endTime,
                                        Projections.fields(
                                                StoreInOrderDTO.class,
                                                store.id,
                                                store.name,
                                                store.minOrderPrice,
                                                store.backgroundImageUrl,
                                                store.distance
                                        ).as("store"),
                                        order.destination,
                                        list(
                                                Projections.fields(
                                                UserInfoDTO.class,
                                                user.id,
                                                user.profileImageUrl
                                            )
                                        ).as("users")
                                )
                        )
                );
    }
}
