package com.proceed.swhackathon.repository;

import com.proceed.swhackathon.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("select o from Order o join fetch o.store s where o.id = :id")
    Optional<Order> findOrderByIdWithStore(Long id);
}
