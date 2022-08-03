package com.proceed.swhackathon.repository;

import com.proceed.swhackathon.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {

    @Query("select od from OrderDetail od where od.menu.store = :store")
    List<OrderDetail> findByStore(Store store);

    @Query("select od from OrderDetail od where od.user = :user and od.order = :order")
    List<OrderDetail> findByUserAndOrder(User user, Order order);

    @Query("select od from OrderDetail od where od.menu = :menu")
    List<OrderDetail> findByMenu(Menu menu);

    @Query("select od from OrderDetail od where od.user = :user and od.order = :order and  od.menu = :menu")
    List<OrderDetail> findByUserAndOrderAndMenu(User user, Order order, Menu menu);
}
