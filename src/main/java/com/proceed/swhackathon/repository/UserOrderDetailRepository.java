package com.proceed.swhackathon.repository;

import com.proceed.swhackathon.model.Order;
import com.proceed.swhackathon.model.UserOrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserOrderDetailRepository extends JpaRepository<UserOrderDetail, Long> {

    @Query("select uod from UserOrderDetail uod where uod.order = :order")
    List<UserOrderDetail> findByOrder(Order order);
}
