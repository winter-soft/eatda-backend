package com.proceed.swhackathon.repository;

import com.proceed.swhackathon.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {

    @Query("select od from OrderDetail od where od.user = :user and od.order = :order and od.menuCheck = true")
    List<OrderDetail> findByUserAndOrder(@Param("user")User user, @Param("order")Order order);

    @Query("select od from OrderDetail od where od.user = :user and od.menuCheck = true")
    List<OrderDetail> findByUser(@Param("user")User user);

    @Query("select od from OrderDetail od where od.menu = :menu")
    List<OrderDetail> findByMenu(@Param("menu")Menu menu);

    @Query("select od from OrderDetail od join fetch od.userOrderDetail uod where uod = :uod and od.user = :user")
    List<OrderDetail> selectUOD(@Param("uod")UserOrderDetail uod, @Param("user")User user);

    @Query("select od from OrderDetail od join fetch od.order odo where od.menuCheck = true and odo <> :order and od.user = :user")
    List<OrderDetail> findAllByMenuCheckIsFalseAndOrder(@Param("user")User user, @Param("order")Order order);

    List<OrderDetail> deleteAllByMenuCheckIsFalseAndUserOrderDetailIsNull();
}
