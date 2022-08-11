package com.proceed.swhackathon.repository;

import com.proceed.swhackathon.model.Order;
import com.proceed.swhackathon.model.User;
import com.proceed.swhackathon.model.UserOrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserOrderDetailRepository extends JpaRepository<UserOrderDetail, Long> {

    @Query("select uod from UserOrderDetail uod where uod.order = :order")
    List<UserOrderDetail> findByOrderAll(Order order);

    @Query("select uod from UserOrderDetail uod where uod.order = :order and uod.user = :user")
    Optional<UserOrderDetail> findByOrderAndUser(Order order, User user);

    @Query("select uod from UserOrderDetail uod join fetch uod.order o where o = :order and uod.user = :user")
    Optional<UserOrderDetail> findByOrderAndUserWithOrder(Order order, User user);
}
