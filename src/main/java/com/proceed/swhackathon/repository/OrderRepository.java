package com.proceed.swhackathon.repository;

import com.proceed.swhackathon.model.Destination;
import com.proceed.swhackathon.model.Order;
import com.proceed.swhackathon.model.OrderStatus;
import com.proceed.swhackathon.model.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface  OrderRepository extends JpaRepository<Order, Long>, OrderRepositoryCustom {

    @Query("select o from Order o join fetch o.store s where o.id = :id")
    Optional<Order> findOrderByIdWithStore(@Param("id")Long id);

    @Query("select o from Order o where o.store = :store")
    List<Order> findByStore(@Param("store")Store store);

    Page<Order> findAllByOrderStatus(Pageable pageable, OrderStatus orderStatus);

    @Query("select o from Order o join fetch o.store s where o.orderStatus = :orderStatus order by o.currentAmount desc ")
    List<Order> findByOrderStatusWithStoreOrderByCurrentAmountDesc(@Param("orderStatus")OrderStatus orderStatus);

    @Query(value = "select * from ORDERS o inner join Destination d ON o.destination_id = d.destination_id where o.destination_id = :destination and DATE_FORMAT(o.endTime, '%Y-%m-%d %H:%i') = :dateTime", nativeQuery = true)
    List<Order> findAllByDestinationAndEndTime(@Param("destination")Long destination, @Param("dateTime")String dateTime);

    @Query(value = "select * from ORDERS o where DATE_FORMAT(o.endTime, '%Y-%m-%d %H:%i') = :dateTime", nativeQuery = true)
    List<Order> findAllByEndTime(@Param("dateTime")String dateTime);

    @Query(value = "select * from ORDERS o where DATE_FORMAT(o.endTime, '%Y-%m-%d') = :dateTime and o.store_id = :storeId", nativeQuery = true)
    List<Order> findAllByEndTimeAndStore(@Param("dateTime")String dateTime, @Param("storeId")Long storeId);
}
